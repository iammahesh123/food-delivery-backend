package com.food.fooddeliverybackend.service;

import com.food.fooddeliverybackend.model.FoodItemRequestDTO;
import com.food.fooddeliverybackend.model.FoodItemResponseDTO;

import java.util.List;

public interface FoodItemService {
    FoodItemResponseDTO addFood(FoodItemRequestDTO foodRequestDTO);
    FoodItemResponseDTO updateFood(Long id,FoodItemRequestDTO foodRequestDTO);
    List<FoodItemResponseDTO> getAllFoods();
    FoodItemResponseDTO getFoodById(Long id);
    void deleteFoodById(Long id);
}
