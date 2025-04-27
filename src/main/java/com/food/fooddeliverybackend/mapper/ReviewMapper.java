package com.food.fooddeliverybackend.mapper;

import com.food.fooddeliverybackend.entity.ReviewEntity;
import com.food.fooddeliverybackend.model.ReviewResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {
    public ReviewResponseDTO toDTO(ReviewEntity reviewEntity, ModelMapper modelMapper) {
        ReviewResponseDTO dto = modelMapper.map(reviewEntity, ReviewResponseDTO.class);
        dto.setFoodItemId(reviewEntity.getFoodItemEntity() != null ? reviewEntity.getFoodItemEntity().getId() : 0L);
        dto.setRestaurantId(reviewEntity.getRestaurantEntity() != null ? reviewEntity.getRestaurantEntity().getId() : 0L);
        return dto;
    }
}
