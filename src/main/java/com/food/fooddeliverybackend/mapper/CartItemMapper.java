package com.food.fooddeliverybackend.mapper;

import com.food.fooddeliverybackend.entity.CartItemEntity;
import com.food.fooddeliverybackend.model.CartItemResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CartItemMapper {
    public CartItemResponseDTO toDTO(CartItemEntity cartItemEntity, ModelMapper modelMapper) {
        return modelMapper.map(cartItemEntity, CartItemResponseDTO.class);
    }
}
