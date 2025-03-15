package com.food.fooddeliverybackend.mapper;

import com.food.fooddeliverybackend.entity.PromoCodeEntity;
import com.food.fooddeliverybackend.model.PromoCodeResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PromoCodeMapper {
    public PromoCodeResponseDTO toDTO(PromoCodeEntity promoCodeEntity, ModelMapper modelMapper) {
        return modelMapper.map(promoCodeEntity, PromoCodeResponseDTO.class);
    }
}
