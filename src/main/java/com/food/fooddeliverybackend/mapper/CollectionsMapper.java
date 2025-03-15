package com.food.fooddeliverybackend.mapper;

import com.food.fooddeliverybackend.entity.CollectionEntity;
import com.food.fooddeliverybackend.model.CollectionResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CollectionsMapper {
    public CollectionResponseDTO toCollectionsDTO(CollectionEntity collections, ModelMapper modelMapper) {
        return modelMapper.map(collections, CollectionResponseDTO.class);
    }
}
