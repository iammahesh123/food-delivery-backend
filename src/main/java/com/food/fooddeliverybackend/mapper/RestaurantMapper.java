package com.food.fooddeliverybackend.mapper;

import com.food.fooddeliverybackend.entity.RestaurantEntity;
import com.food.fooddeliverybackend.model.FoodItemResponseDTO;
import com.food.fooddeliverybackend.model.RestaurantResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMapper {

    public RestaurantResponseDTO toDTO(RestaurantEntity restaurant, ModelMapper modelMapper) {
        RestaurantResponseDTO dto = modelMapper.map(restaurant, RestaurantResponseDTO.class);
        dto.setCuisines(restaurant.getCuisineTypes());

        if (restaurant.getFoodItemEntities() != null) {
            dto.setFoodItemsIds(
                    restaurant.getFoodItemEntities().stream()
                            .map(foodItem -> modelMapper.map(foodItem, FoodItemResponseDTO.class))
                            .toList()
            );
        }

        return dto;
    }
}
