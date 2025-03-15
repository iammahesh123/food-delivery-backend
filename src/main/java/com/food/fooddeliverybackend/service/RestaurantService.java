package com.food.fooddeliverybackend.service;

import com.food.fooddeliverybackend.model.RestaurantRequestDTO;
import com.food.fooddeliverybackend.model.RestaurantResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RestaurantService {
    RestaurantResponseDTO create(RestaurantRequestDTO restaurantRequestDTO);
    RestaurantResponseDTO update(Long id,RestaurantRequestDTO restaurantRequestDTO);
    RestaurantResponseDTO get(Long id);
    List<RestaurantResponseDTO> getAll();
    RestaurantResponseDTO delete(Long id);
}
