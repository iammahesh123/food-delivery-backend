package com.food.fooddeliverybackend.mapper;

import com.food.fooddeliverybackend.entity.OrdersEntity;
import com.food.fooddeliverybackend.model.OrderResponseDTO;
import jakarta.persistence.criteria.Order;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public OrderResponseDTO orderResponseDTO(OrdersEntity order, ModelMapper modelMapper) {
        return modelMapper.map(order, OrderResponseDTO.class);
    }
}
