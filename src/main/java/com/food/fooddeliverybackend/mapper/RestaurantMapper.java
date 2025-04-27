package com.food.fooddeliverybackend.mapper;

import com.food.fooddeliverybackend.entity.FoodItemEntity;
import com.food.fooddeliverybackend.entity.RestaurantEntity;
import com.food.fooddeliverybackend.entity.ReviewEntity;
import com.food.fooddeliverybackend.model.FoodItemResponseDTO;
import com.food.fooddeliverybackend.model.RestaurantResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMapper {

    public RestaurantResponseDTO toDTO(RestaurantEntity restaurant, ModelMapper modelMapper) {
        RestaurantResponseDTO dto = modelMapper.map(restaurant, RestaurantResponseDTO.class);
        dto.setCuisines(restaurant.getCuisineTypes());
        dto.setAmenities(restaurant.getAmenities());

        if (restaurant.getFoodItemEntities() != null) {
            dto.setFoodItemsIds(restaurant.getFoodItemEntities().stream().map(FoodItemEntity::getId).toList());
        }
        if(restaurant.getReviewEntities() != null) {
            dto.setReviewsIds(restaurant.getReviewEntities().stream().map(ReviewEntity::getId).toList());
        }

        return dto;
    }
}
