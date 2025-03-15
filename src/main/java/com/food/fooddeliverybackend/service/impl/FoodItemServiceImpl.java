package com.food.fooddeliverybackend.service.impl;

import com.food.fooddeliverybackend.entity.FoodItemEntity;
import com.food.fooddeliverybackend.model.FoodItemRequestDTO;
import com.food.fooddeliverybackend.model.FoodItemResponseDTO;
import com.food.fooddeliverybackend.repository.FoodItemRepository;
import com.food.fooddeliverybackend.service.FoodItemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodItemServiceImpl implements FoodItemService {
    private final FoodItemRepository foodItemRepository;

    public FoodItemServiceImpl(FoodItemRepository foodItemRepository) {
        this.foodItemRepository = foodItemRepository;
    }

    @Override
    public FoodItemResponseDTO addFood(FoodItemRequestDTO foodRequestDTO) {
        FoodItemEntity food = new FoodItemEntity();
        food.setName(foodRequestDTO.getName());
        food.setDescription(foodRequestDTO.getDescription());
        food.setPrice(foodRequestDTO.getPrice());
        food.setCategory(foodRequestDTO.getCategory());
        food.setImageUrl(foodRequestDTO.getImageUrl());
        FoodItemEntity savedFood = foodItemRepository.save(food);
        return mapToDTO(savedFood);
    }

    @Override
    public List<FoodItemResponseDTO> getAllFoods() {
        return foodItemRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FoodItemResponseDTO getFoodById(Long id) {
        FoodItemEntity food = foodItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food not found"));
        return mapToDTO(food);
    }

    private FoodItemResponseDTO mapToDTO(FoodItemEntity food) {
        FoodItemResponseDTO dto = new FoodItemResponseDTO();
        dto.setId(food.getId());
        dto.setName(food.getName());
        dto.setDescription(food.getDescription());
        dto.setPrice(food.getPrice());
        dto.setCategory(food.getCategory());
        dto.setImageUrl(food.getImageUrl());
        return dto;
    }
}
