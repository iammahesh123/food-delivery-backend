package com.food.fooddeliverybackend.controller;

import com.food.fooddeliverybackend.model.OrderRequestDTO;
import com.food.fooddeliverybackend.model.OrderResponseDTO;
import com.food.fooddeliverybackend.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        return ResponseEntity.ok().body(orderService.createOrder(orderRequestDTO));
    }

    @PutMapping("/{id}")
    ResponseEntity<OrderResponseDTO> updateOrder(@PathVariable("id") Long id, @RequestBody OrderRequestDTO orderRequestDTO) {
        return ResponseEntity.ok().body(orderService.updateOrder(id, orderRequestDTO));
    }

    @GetMapping("/{id}")
    ResponseEntity<OrderResponseDTO> getOrder(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(orderService.getOrderById(id));
    }

    @GetMapping
    ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        return ResponseEntity.ok().body(orderService.getAllOrders());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<HttpStatus> deleteOrder(@PathVariable("id") Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok().body(HttpStatus.OK);
    }
}
