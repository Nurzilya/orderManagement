package com.exm.ordermanagement;

import com.exm.ordermanagement.dto.OrderRequestDTO;
import com.exm.ordermanagement.entity.*;
import com.exm.ordermanagement.dto.PaymentRequestDTO;
import com.exm.ordermanagement.dto.PaymentResponseDTO;
import com.exm.ordermanagement.repository.OrderRepository;
import com.exm.ordermanagement.repository.ProductRepository;
import com.exm.ordermanagement.service.MockPaymentService;
import com.exm.ordermanagement.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private MockPaymentService paymentService;

    @InjectMocks
    private OrderService orderService;

    private Product product1;
    private Product product2;
    private Order order;

    @BeforeEach
    void setUp() {
        product1 = new Product("White vine", 1.5D, 2, null, "whiteVine.jpg");
        product1.setId(1L);

        product2 = new Product("Orange juice", 1.00D, 3, null, "orangeJuice.jpg");
        product2.setId(2L);

        order = new Order();
        order.setId(1L);
        order.setProducts(List.of(product1, product2));
        order.setStatus(OrderStatus.OPEN);
        order.setBuyerDetails(new BuyerDetails("customer@example.com", 'A', (short)10));
    }

    // Test order create
    @Test
    void testCreateOrder_Success() {
        OrderRequestDTO request = new OrderRequestDTO();
        request.setProductIds(List.of(1L, 2L));
        request.setEmail("customer@example.com");
        request.setSeatLetter('A');
        request.setSeatNumber((short) 10);
        when(productRepository.findAllById(request.getProductIds())).thenReturn(List.of(product1, product2));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order createdOrder = orderService.createOrder(request);

        assertNotNull(createdOrder);
        assertEquals(OrderStatus.OPEN, createdOrder.getStatus());
        assertEquals(2, createdOrder.getProducts().size());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    // Test order update
    @Test
    void testUpdateOrder_AddProducts() {
        List<Long> newProductIds = List.of(1L, 2L);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(productRepository.findAllById(newProductIds)).thenReturn(List.of(product1, product2));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
        orderRequestDTO.setProductIds(newProductIds);
        orderRequestDTO.setEmail("customer@example.com");

        Order updatedOrder = orderService.updateOrder(1L, orderRequestDTO);

        assertNotNull(updatedOrder);
        assertEquals(2, updatedOrder.getProducts().size());
        verify(orderRepository, times(1)).save(order);
    }

    //Test order cancel
    @Test
    void testCancelOrder_Success() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order canceledOrder = orderService.cancelOrder(1L);

        assertEquals(OrderStatus.CANCELED, canceledOrder.getStatus());
        verify(orderRepository, times(1)).save(order);
    }

    // Test order payment successful
    @Test
    void testFinishOrder_PaymentSuccess() {
        PaymentRequestDTO paymentRequest = new PaymentRequestDTO("1234-5678-9012-3456", 3700.00, "Stripe");
        PaymentResponseDTO paymentResponse = new PaymentResponseDTO(PaymentStatus.PAID, LocalDateTime.now(), "TXN123");

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(paymentService.processPayment(paymentRequest)).thenReturn(paymentResponse);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order finishedOrder = orderService.finishOrder(1L, paymentRequest);

        assertEquals(OrderStatus.FINISHED, finishedOrder.getStatus());
        assertEquals(PaymentStatus.PAID, finishedOrder.getPaymentDetails().getPaymentStatus());
        verify(orderRepository, times(1)).save(order);
    }

    // Test order payment failed
    @Test
    void testFinishOrder_PaymentFailed() {
        PaymentRequestDTO paymentRequest = new PaymentRequestDTO("1234-5678-9012-3456", 3700.00, "Stripe");
        PaymentResponseDTO paymentResponse = new PaymentResponseDTO(PaymentStatus.PAYMENT_FAILED, LocalDateTime.now(), "TXN123");

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(paymentService.processPayment(paymentRequest)).thenReturn(paymentResponse);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order droppedOrder = orderService.finishOrder(1L, paymentRequest);

        assertEquals(OrderStatus.DROPPED, droppedOrder.getStatus());
        assertEquals(PaymentStatus.PAYMENT_FAILED, droppedOrder.getPaymentDetails().getPaymentStatus());
        verify(orderRepository, times(1)).save(order);
    }
}
