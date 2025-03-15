package com.food.fooddeliverybackend.mapper;

import com.food.fooddeliverybackend.entity.CartEntity;
import com.food.fooddeliverybackend.model.CartResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CartMapper {
    public CartResponseDTO toDTO(CartEntity cartEntity, ModelMapper modelMapper) {
        return modelMapper.map(cartEntity, CartResponseDTO.class);
    }
}
