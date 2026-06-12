package com.food.fooddeliverybackend.model;

import com.food.fooddeliverybackend.enums.OrderStatus;
import com.food.fooddeliverybackend.enums.PaymentMethod;
import com.food.fooddeliverybackend.enums.PaymentStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {
    private Long id;
    private String name;
    private double totalAmount;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    private String deliveryAddress;
    private LocalDateTime orderDate;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    private Long userId;
    private String customerName;
    private Long restaurantId;
    private String restaurantName;
    private Long deliveryAgentId;
    private String deliveryAgentName;
    private List<Long> orderItemIds;
    private List<OrderItemDetailDTO> items;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
