package com.food.fooddeliverybackend.service.impl;

import com.food.fooddeliverybackend.entity.FoodItemEntity;
import com.food.fooddeliverybackend.entity.RestaurantEntity;
import com.food.fooddeliverybackend.mapper.FoodItemMapper;
import com.food.fooddeliverybackend.model.FoodItemRequestDTO;
import com.food.fooddeliverybackend.model.FoodItemResponseDTO;
import com.food.fooddeliverybackend.repository.FoodItemRepository;
import com.food.fooddeliverybackend.repository.RestaurantRepository;
import com.food.fooddeliverybackend.service.FoodItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodItemServiceImpl implements FoodItemService {
    private final FoodItemRepository foodItemRepository;
    private final FoodItemMapper foodItemMapper;
    private final ModelMapper modelMapper;
    private final RestaurantRepository restaurantRepository;

    public FoodItemServiceImpl(FoodItemRepository foodItemRepository, FoodItemMapper foodItemMapper, ModelMapper modelMapper, RestaurantRepository restaurantRepository) {
        this.foodItemRepository = foodItemRepository;
        this.foodItemMapper = foodItemMapper;
        this.modelMapper = modelMapper;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public FoodItemResponseDTO addFood(FoodItemRequestDTO foodRequestDTO) {
        FoodItemEntity food = new FoodItemEntity();
        BeanUtils.copyProperties(foodRequestDTO, food);
        if(foodRequestDTO.getRestaurantId() != null) {
            RestaurantEntity restaurant = restaurantRepository.findById(foodRequestDTO.getRestaurantId()).orElse(null);
            food.setRestaurantEntity(restaurant);
            restaurantRepository.save(restaurant);
        }
        FoodItemEntity foodItem = foodItemRepository.save(food);
        return foodItemMapper.foodItemResponseDTO(foodItem,modelMapper);
    }

    @Override
    public FoodItemResponseDTO updateFood(Long id, FoodItemRequestDTO foodRequestDTO) {
        FoodItemEntity existingFood = foodItemRepository.findById(id).orElse(null);
        BeanUtils.copyProperties(foodRequestDTO, existingFood);
        if(foodRequestDTO.getRestaurantId() != null) {
            RestaurantEntity restaurant = restaurantRepository.findById(foodRequestDTO.getRestaurantId()).orElse(null);
            existingFood.setRestaurantEntity(restaurant);
            restaurantRepository.save(restaurant);
        }
        FoodItemEntity updatedItem = foodItemRepository.save(existingFood);
        return foodItemMapper.foodItemResponseDTO(updatedItem,modelMapper);
    }

    @Override
    public List<FoodItemResponseDTO> getAllFoods() {
        List<FoodItemEntity> foods = foodItemRepository.findAll();
        return foods.stream().map(food -> foodItemMapper.foodItemResponseDTO(food,modelMapper)).collect(Collectors.toList());
    }

    @Override
    public FoodItemResponseDTO getFoodById(Long id) {
        FoodItemEntity food = foodItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food not found"));
        return foodItemMapper.foodItemResponseDTO(food,modelMapper);
    }

    @Override
    public void deleteFoodById(Long id) {
        FoodItemEntity item = foodItemRepository.findById(id).orElse(null);
        foodItemRepository.delete(item);
    }
}
