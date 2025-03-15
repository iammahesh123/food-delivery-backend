package com.food.fooddeliverybackend.model;

import com.food.fooddeliverybackend.enums.CuisineType;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
