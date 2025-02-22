package com.exm.ordermanagement.controller;

import com.exm.ordermanagement.dto.OrderRequestDTO;
import com.exm.ordermanagement.dto.PaymentRequestDTO;
import com.exm.ordermanagement.service.OrderService;
import com.exm.ordermanagement.entity.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Order API", description = "Endpoints for managing orders")
public class OrderController {
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    @Operation(summary = "Get all Orders", description = "Retrieves a list of all orders.")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PostMapping
    @Operation(summary = "Create an Order", description = "Creates a new order with given products and buyer details.")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequestDTO orderRequest) {
        Order order = orderService.createOrder(orderRequest);
        return ResponseEntity.ok(order);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update order", description = "Update order with current id.")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody OrderRequestDTO orderRequestDTO) {
        Order targetOrder = orderService.updateOrder(id, orderRequestDTO);
        return ResponseEntity.ok(targetOrder);
    }

    @PutMapping("/{id}/cancel")
    @Operation(summary = "Cancel order", description = "Cancel order with current id.")
    public ResponseEntity<Order> cancelOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.cancelOrder(id));
    }

    @PutMapping("/{id}/finish")
    @Operation(summary = "Finish order", description = "Finish order with current id.")
    public ResponseEntity<Order> finishOrder(@PathVariable Long id, @RequestBody PaymentRequestDTO paymentRequest) {
        return ResponseEntity.ok(orderService.finishOrder(id, paymentRequest));
    }
}
