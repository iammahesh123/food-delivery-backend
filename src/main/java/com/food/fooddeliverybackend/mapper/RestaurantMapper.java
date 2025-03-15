package com.food.fooddeliverybackend.mapper;

import com.food.fooddeliverybackend.entity.RestaurantEntity;
import com.food.fooddeliverybackend.model.RestaurantResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMapper {

    public RestaurantResponseDTO toDTO(RestaurantEntity restaurant, ModelMapper modelMapper) {
        return modelMapper.map(restaurant, RestaurantResponseDTO.class);
    }
}
