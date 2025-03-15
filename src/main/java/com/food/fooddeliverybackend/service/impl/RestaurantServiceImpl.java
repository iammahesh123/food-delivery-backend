package com.food.fooddeliverybackend.service.impl;

import com.food.fooddeliverybackend.entity.CollectionEntity;
import com.food.fooddeliverybackend.entity.FoodItemEntity;
import com.food.fooddeliverybackend.entity.RestaurantEntity;
import com.food.fooddeliverybackend.entity.UserEntity;
import com.food.fooddeliverybackend.exception.ResourceNotFoundException;
import com.food.fooddeliverybackend.mapper.RestaurantMapper;
import com.food.fooddeliverybackend.model.RestaurantRequestDTO;
import com.food.fooddeliverybackend.model.RestaurantResponseDTO;
import com.food.fooddeliverybackend.repository.CollectionsRepository;
import com.food.fooddeliverybackend.repository.FoodItemRepository;
import com.food.fooddeliverybackend.repository.RestaurantRepository;
import com.food.fooddeliverybackend.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final FoodItemRepository foodItemRepository;
    private final CollectionsRepository collectionsRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, RestaurantMapper restaurantMapper, ModelMapper modelMapper, UserRepository userRepository, FoodItemRepository foodItemRepository, CollectionsRepository collectionsRepository) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantMapper = restaurantMapper;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.foodItemRepository = foodItemRepository;
        this.collectionsRepository = collectionsRepository;
    }

    @Override
    public RestaurantResponseDTO create(RestaurantRequestDTO restaurantRequestDTO) {
        RestaurantEntity restaurant = new RestaurantEntity();
        BeanUtils.copyProperties(restaurantRequestDTO, restaurant);
        if(restaurantRequestDTO.getOwnerId() != null){
            UserEntity owner = userRepository.findById(restaurantRequestDTO.getOwnerId()).orElse(null);
            restaurant.setOwner(owner);
        }
        if(restaurantRequestDTO.getFoodItemsIds() != null) {
            List<FoodItemEntity> foodItems = foodItemRepository.findAllById(restaurantRequestDTO.getFoodItemsIds());
            for (FoodItemEntity foodItem : foodItems) {
                foodItem.setRestaurantEntity(restaurant);
                foodItemRepository.save(foodItem);
            }
            restaurant.setFoodItemEntities(foodItems);
        }
        if(restaurantRequestDTO.getCollectionId() != null) {
            CollectionEntity collections = collectionsRepository.findById(restaurantRequestDTO.getCollectionId()).orElse(null);
            restaurant.setCollectionEntity(collections);
        }
        RestaurantEntity savedRestaurant = restaurantRepository.save(restaurant);
        return restaurantMapper.toDTO(savedRestaurant,modelMapper);
    }

    @Override
    public RestaurantResponseDTO update(Long id, RestaurantRequestDTO restaurantRequestDTO) {
        RestaurantEntity restaurant = restaurantRepository.findById(id).orElse(null);
        BeanUtils.copyProperties(restaurantRequestDTO, restaurant);
        if (restaurantRequestDTO.getOwnerId() != null) {
            UserEntity owner = userRepository.findById(restaurantRequestDTO.getOwnerId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + restaurantRequestDTO.getOwnerId()));
            restaurant.setOwner(owner);
        }
        if(restaurantRequestDTO.getCollectionId() != null) {
            CollectionEntity collections = collectionsRepository.findById(restaurantRequestDTO.getCollectionId()).orElse(null);
            restaurant.setCollectionEntity(collections);
        }
        if (restaurantRequestDTO.getFoodItemsIds() != null && !restaurantRequestDTO.getFoodItemsIds().isEmpty()) {
            List<FoodItemEntity> foodItems = foodItemRepository.findAllById(restaurantRequestDTO.getFoodItemsIds());
            if (foodItems.size() != restaurantRequestDTO.getFoodItemsIds().size()) {
                throw new ResourceNotFoundException("One or more food items not found");
            }
            restaurant.getFoodItemEntities().clear();
            for (FoodItemEntity foodItem : foodItems) {
                foodItem.setRestaurantEntity(restaurant);
            }
            restaurant.setFoodItemEntities(foodItems);
        }
        RestaurantEntity updatedRestaurant = restaurantRepository.save(restaurant);
        return restaurantMapper.toDTO(updatedRestaurant,modelMapper);
    }

    @Override
    public RestaurantResponseDTO get(Long id) {
        RestaurantEntity restaurant = restaurantRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + id));
        return restaurantMapper.toDTO(restaurant,modelMapper);
    }

    @Override
    public List<RestaurantResponseDTO> getAll() {
        List<RestaurantEntity> restaurants = restaurantRepository.findAll();
        return restaurants.stream().map(restaurant -> restaurantMapper.toDTO(restaurant,modelMapper)).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        RestaurantEntity restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + id));
        restaurantRepository.delete(restaurant);
    }
}
