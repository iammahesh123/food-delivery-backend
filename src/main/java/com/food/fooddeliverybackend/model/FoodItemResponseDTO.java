package com.food.fooddeliverybackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodItemResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String category;
    private String imageUrl;
    private Long restaurantId;
    private List<Long> reviewIds;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
