package com.food.fooddeliverybackend.mapper;

import com.food.fooddeliverybackend.entity.FoodItemEntity;
import com.food.fooddeliverybackend.model.FoodItemResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class FoodItemMapper {
    public FoodItemResponseDTO foodItemResponseDTO(FoodItemEntity foodItemEntity, ModelMapper modelMapper) {
        FoodItemResponseDTO foodItemResponseDTO =  modelMapper.map(foodItemEntity, FoodItemResponseDTO.class);
        if (foodItemEntity != null && foodItemEntity.getRestaurantEntity() != null) {
            foodItemResponseDTO.setRestaurantId(foodItemEntity.getRestaurantEntity().getId());
        }
        return foodItemResponseDTO;
    }
}
