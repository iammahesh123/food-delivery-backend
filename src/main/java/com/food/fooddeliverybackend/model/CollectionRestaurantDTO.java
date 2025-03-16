package com.food.fooddeliverybackend.model;

import com.food.fooddeliverybackend.enums.CuisineType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectionRestaurantDTO {
    private String restaurantName;
    private String description;
    private List<CuisineType> cuisines;
    private double rating;
    private String address;
}
