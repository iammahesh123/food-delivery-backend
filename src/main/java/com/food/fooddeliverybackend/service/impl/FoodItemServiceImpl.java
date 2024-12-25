package com.food.fooddeliverybackend.service.impl;

import com.food.fooddeliverybackend.entity.FoodItem;
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
        FoodItem food = new FoodItem();
        food.setName(foodRequestDTO.getName());
        food.setDescription(foodRequestDTO.getDescription());
        food.setPrice(foodRequestDTO.getPrice());
        food.setCategory(foodRequestDTO.getCategory());
        try {
            food.setImage(foodRequestDTO.getImage().getBytes());
        } catch (Exception e) {
            throw new RuntimeException("Error processing image", e);
        }

        FoodItem savedFood = foodItemRepository.save(food);
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
        FoodItem food = foodItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food not found"));
        return mapToDTO(food);
    }

    public byte[] getFoodImageById(Long id) {
        FoodItem food = foodItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food not found"));
        return food.getImage();
    }


    private FoodItemResponseDTO mapToDTO(FoodItem food) {
        FoodItemResponseDTO dto = new FoodItemResponseDTO();
        dto.setId(food.getId());
        dto.setName(food.getName());
        dto.setDescription(food.getDescription());
        dto.setPrice(food.getPrice());
        dto.setCategory(food.getCategory());
        dto.setImageUrl("/api/foods/images/" + food.getId()); // Endpoint to retrieve the image
        return dto;
    }
}
