package com.food.fooddeliverybackend.model;

import com.food.fooddeliverybackend.enums.CuisineType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantResponseDTO {
    private Long id;
    private String restaurantName;
    private String description;
    private String address;
    private String phone;
    private List<CuisineType> cuisines;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private double rating;
    private String imageUrl;

    private Long ownerId;
    private List<Long> foodItemsIds;
    private List<Long> ordersIds;
    private Long collectionId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
