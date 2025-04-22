package com.food.fooddeliverybackend.mapper;

import com.food.fooddeliverybackend.entity.AddressEntity;
import com.food.fooddeliverybackend.model.AddressResponseDTO;
import jakarta.mail.Address;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
    public AddressResponseDTO toDTO(AddressEntity address, ModelMapper modelMapper) {
        return modelMapper.map(address, AddressResponseDTO.class);
    }
}
