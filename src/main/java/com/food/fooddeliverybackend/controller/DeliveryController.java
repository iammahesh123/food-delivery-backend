package com.food.fooddeliverybackend.controller;

import com.food.fooddeliverybackend.enums.OrderStatus;
import com.food.fooddeliverybackend.model.OrderResponseDTO;
import com.food.fooddeliverybackend.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Delivery Controller", description = "Endpoints for the delivery partner portal")
@RestController
@RequestMapping("/deliveries")
public class DeliveryController {

    private final OrderService orderService;

    public DeliveryController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "List orders ready for pickup with no delivery agent assigned")
    @GetMapping("/available")
    public ResponseEntity<List<OrderResponseDTO>> getAvailableDeliveries() {
        return ResponseEntity.ok().body(orderService.getAvailableDeliveries());
    }

    @Operation(summary = "List deliveries assigned to the current delivery agent")
    @GetMapping("/my")
    public ResponseEntity<List<OrderResponseDTO>> getMyDeliveries() {
        return ResponseEntity.ok().body(orderService.getMyDeliveries());
    }

    @Operation(summary = "Accept a delivery (assigns the order to the current agent)")
    @PostMapping("/{orderId}/accept")
    public ResponseEntity<OrderResponseDTO> acceptDelivery(@PathVariable("orderId") Long orderId) {
        return ResponseEntity.ok().body(orderService.acceptDelivery(orderId));
    }

    @Operation(summary = "Update delivery status (OUT_FOR_DELIVERY or DELIVERED)")
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderResponseDTO> updateDeliveryStatus(
            @PathVariable("orderId") Long orderId,
            @RequestParam("status") OrderStatus status) {
        return ResponseEntity.ok().body(orderService.updateDeliveryStatus(orderId, status));
    }
}
