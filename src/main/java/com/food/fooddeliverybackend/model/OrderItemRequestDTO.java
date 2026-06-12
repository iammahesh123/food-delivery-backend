package com.food.fooddeliverybackend.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemRequestDTO {

    @NotNull(message = "foodItemId is required")
    private Long foodItemId;

    @Min(value = 1, message = "quantity must be at least 1")
    private int quantity;
}
