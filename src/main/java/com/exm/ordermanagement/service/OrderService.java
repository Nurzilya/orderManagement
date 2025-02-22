package com.exm.ordermanagement.service;

import com.exm.ordermanagement.dto.OrderRequestDTO;
import com.exm.ordermanagement.dto.PaymentRequestDTO;
import com.exm.ordermanagement.dto.PaymentResponseDTO;
import com.exm.ordermanagement.entity.*;
import com.exm.ordermanagement.repository.OrderRepository;
import com.exm.ordermanagement.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final MockPaymentService paymentService;

    @Autowired
    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, MockPaymentService paymentService) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.paymentService = paymentService;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    public Order createOrder(OrderRequestDTO orderRequest) {
        try {
            List<Product> products = productRepository.findAllById(orderRequest.getProductIds());

            double totalPrice = products.stream().mapToDouble(Product::getPrice).sum();

            for (Product product : products) {
                if (product.getStockQuantity() < 1) {
                    throw new IllegalArgumentException("Not enough stock for product: " + product.getName());
                }
            }

            for (Product product : products) {
                product.reduceStock(1);// Reduce stock by 1 per product added
                productRepository.save(product);
            }
            Order order = new Order();
            order.setPaymentDetails(new PaymentDetails(totalPrice));
            order.setProducts(products);
            order.setStatus(OrderStatus.OPEN);
            order.setBuyerDetails(new BuyerDetails(orderRequest.getEmail(), orderRequest.getSeatLetter(), orderRequest.getSeatNumber()));

            return orderRepository.save(order);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Order creation failed: Duplicate entry detected", e);
        }
    }

    @Transactional
    public Order updateOrder(Long orderId, OrderRequestDTO orderRequestDTO) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        List<Long> newProductsIds = orderRequestDTO.getProductIds();
        List<Product> newProducts = productRepository.findAllById(newProductsIds);

        for (Product product : newProducts) {
            if (product.getStockQuantity() < 1) {
                throw new IllegalArgumentException("Not enough stock for product: " + product.getName());
            }
        }

        for (Product product : newProducts) {
            product.reduceStock(1);
            productRepository.save(product);
        }

        double totalPrice = newProducts.stream().mapToDouble(Product::getPrice).sum();

        order.setPaymentDetails(new PaymentDetails(totalPrice));
        order.setProducts(newProducts);
        order.setBuyerDetails(new BuyerDetails(order.getBuyerDetails().getSeatLetter(), order.getBuyerDetails().getSeatNumber()));

        return orderRepository.save(order);
    }


    @Transactional
    public Order updateOrder(Long orderId, List<Long> productIds) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        List<Product> products = productRepository.findAllById(productIds);

        double totalPrice = products.stream().mapToDouble(Product::getPrice).sum();

        PaymentDetails paymentDetails = order.getPaymentDetails();
        paymentDetails.setTotalPrice(totalPrice);
        order.setPaymentDetails(paymentDetails);
        order.setProducts(products);
        order.setBuyerDetails(new BuyerDetails(order.getBuyerDetails().getSeatLetter(), order.getBuyerDetails().getSeatNumber()));

        return orderRepository.save(order);
    }

    @Transactional
    public Order cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        List<Product> products = order.getProducts();
        for (int i = 0; i < products.size(); i++) {
            products.get(i).increaseStock(1);
            productRepository.save(products.get(i));
        }

        order.setStatus(OrderStatus.CANCELED);
        return orderRepository.save(order);
    }

    @Transactional
    public Order finishOrder(Long orderId, PaymentRequestDTO paymentRequest) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() != OrderStatus.OPEN) {
            throw new IllegalStateException("Order cannot be finished");
        }

        PaymentResponseDTO paymentResponse = paymentService.processPayment(paymentRequest);

        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setCardToken(paymentRequest.getCardToken());
        paymentDetails.setPaymentStatus(paymentResponse.getStatus());
        paymentDetails.setPaymentDate(paymentResponse.getPaymentDate());
        paymentDetails.setPaymentGateway(paymentRequest.getPaymentGateway());

        order.setPaymentDetails(paymentDetails);

        if (paymentResponse.getStatus() == PaymentStatus.PAID) {
            order.setStatus(OrderStatus.FINISHED);
        } else {
            order.setStatus(OrderStatus.DROPPED);
        }

        return orderRepository.save(order);
    }
}
