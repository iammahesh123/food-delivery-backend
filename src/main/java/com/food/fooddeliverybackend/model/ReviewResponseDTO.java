package com.food.fooddeliverybackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDTO {
    private Long id;
    private String title;
    private String comment;
    private int rating;
    private long foodItemId;
    private long restaurantId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
