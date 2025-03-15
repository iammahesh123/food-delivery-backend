package com.food.fooddeliverybackend.model;

import com.food.fooddeliverybackend.entity.FoodItemEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class FoodItemMapper {
    public FoodItemResponseDTO foodItemResponseDTO(FoodItemEntity foodItemEntity, ModelMapper modelMapper) {
        return modelMapper.map(foodItemEntity, FoodItemResponseDTO.class);
    }
}
