package com.food.fooddeliverybackend.model;

import com.food.fooddeliverybackend.enums.Amenities;
import com.food.fooddeliverybackend.enums.CuisineType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantRequestDTO {
    private String restaurantName;
    private String description;
    private String address;
    private String phone;
    private List<CuisineType> cuisines;
    private Set<Amenities> amenities;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private double rating;
    private String imageUrl;

    private Long ownerId;
    private List<Long> foodItemsIds;
    private List<Long> ordersIds;
    private Long collectionId;
}
