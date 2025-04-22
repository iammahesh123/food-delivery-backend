package com.food.fooddeliverybackend.service;

import com.food.fooddeliverybackend.model.FoodItemRequestDTO;
import com.food.fooddeliverybackend.model.FoodItemResponseDTO;
import com.food.fooddeliverybackend.model.PageModel;

import java.util.List;

public interface FoodItemService {
    FoodItemResponseDTO addFood(FoodItemRequestDTO foodRequestDTO);
    FoodItemResponseDTO updateFood(Long id,FoodItemRequestDTO foodRequestDTO);
    List<FoodItemResponseDTO> getAllFoods(PageModel pageModel);
    FoodItemResponseDTO getFoodById(Long id);
    void deleteFoodById(Long id);
}
