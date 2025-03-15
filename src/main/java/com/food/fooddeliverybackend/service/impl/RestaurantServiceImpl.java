package com.food.fooddeliverybackend.service.impl;

import com.food.fooddeliverybackend.entity.RestaurantEntity;
import com.food.fooddeliverybackend.mapper.RestaurantMapper;
import com.food.fooddeliverybackend.model.RestaurantRequestDTO;
import com.food.fooddeliverybackend.model.RestaurantResponseDTO;
import com.food.fooddeliverybackend.repository.RestaurantRepository;
import com.food.fooddeliverybackend.service.RestaurantService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;
    private final ModelMapper modelMapper;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, RestaurantMapper restaurantMapper, ModelMapper modelMapper) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantMapper = restaurantMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public RestaurantResponseDTO create(RestaurantRequestDTO restaurantRequestDTO) {
        RestaurantEntity restaurant = new RestaurantEntity();
        BeanUtils.copyProperties(restaurantRequestDTO, restaurant);
        RestaurantEntity savedRestaurant = restaurantRepository.save(restaurant);
        return restaurantMapper.toDTO(savedRestaurant,modelMapper);
    }

    @Override
    public RestaurantResponseDTO update(Long id, RestaurantRequestDTO restaurantRequestDTO) {
        RestaurantEntity restaurant = restaurantRepository.findById(id).orElse(null);
        BeanUtils.copyProperties(restaurantRequestDTO, restaurant);
        RestaurantEntity savedRestaurant = restaurantRepository.save(restaurant);
        return restaurantMapper.toDTO(savedRestaurant,modelMapper);
    }

    @Override
    public RestaurantResponseDTO get(Long id) {
        RestaurantEntity restaurant = restaurantRepository.findById(id).orElse(null);
        return restaurantMapper.toDTO(restaurant,modelMapper);
    }

    @Override
    public List<RestaurantResponseDTO> getAll() {
        List<RestaurantEntity> restaurants = restaurantRepository.findAll();
        return restaurants.stream().map(restaurant -> restaurantMapper.toDTO(restaurant,modelMapper)).collect(Collectors.toList());
    }

    @Override
    public RestaurantResponseDTO delete(Long id) {
        return null;
    }
}
