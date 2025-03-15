package com.food.fooddeliverybackend.mapper;

import com.food.fooddeliverybackend.entity.ReviewEntity;
import com.food.fooddeliverybackend.model.ReviewResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {
    public ReviewResponseDTO toDTO(ReviewEntity reviewEntity, ModelMapper modelMapper) {
        return modelMapper.map(reviewEntity, ReviewResponseDTO.class);
    }
}
