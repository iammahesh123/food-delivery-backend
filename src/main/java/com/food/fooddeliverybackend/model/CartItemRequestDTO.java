package com.food.fooddeliverybackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemRequestDTO {
    private String title;
    private String description;
    private int quantity;
    private double price;
    private Long cartId;
    private List<Long> foodItemIds;
}
