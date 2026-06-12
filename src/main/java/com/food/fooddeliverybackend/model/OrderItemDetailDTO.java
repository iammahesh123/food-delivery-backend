package com.food.fooddeliverybackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDetailDTO {
    private Long id;
    private Long foodItemId;
    private String title;
    private int quantity;
    private double price;
}
