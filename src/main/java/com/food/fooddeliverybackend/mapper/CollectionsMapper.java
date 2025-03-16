package com.food.fooddeliverybackend.mapper;

import com.food.fooddeliverybackend.entity.CollectionEntity;
import com.food.fooddeliverybackend.model.CollectionResponseDTO;
import com.food.fooddeliverybackend.model.CollectionRestaurantDTO;
import com.food.fooddeliverybackend.model.RestaurantResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CollectionsMapper {
    public CollectionResponseDTO toCollectionsDTO(CollectionEntity collections, ModelMapper modelMapper) {
        CollectionResponseDTO collectionResponseDTO = modelMapper.map(collections, CollectionResponseDTO.class);
        if (collections.getRestaurantEntities() != null && !collections.getRestaurantEntities().isEmpty()) {
            List<CollectionRestaurantDTO> restaurantDTOs = collections.getRestaurantEntities().stream()
                    .map(restaurantEntity -> modelMapper.map(restaurantEntity, CollectionRestaurantDTO.class))
                    .collect(Collectors.toList());
            collectionResponseDTO.setRestaurants(restaurantDTOs);
        }

        return collectionResponseDTO;
    }
}
