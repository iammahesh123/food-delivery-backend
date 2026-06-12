package com.food.fooddeliverybackend.mapper;

import com.food.fooddeliverybackend.entity.OrdersEntity;
import com.food.fooddeliverybackend.model.OrderItemDetailDTO;
import com.food.fooddeliverybackend.model.OrderResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderMapper {
    public OrderResponseDTO orderResponseDTO(OrdersEntity order, ModelMapper modelMapper) {
        if (order == null) {
            return null;
        }
        OrderResponseDTO dto = modelMapper.map(order, OrderResponseDTO.class);
        if (order.getUserEntity() != null) {
            dto.setUserId(order.getUserEntity().getId());
            dto.setCustomerName(order.getUserEntity().getFullName());
        }
        if (order.getRestaurantEntity() != null) {
            dto.setRestaurantId(order.getRestaurantEntity().getId());
            dto.setRestaurantName(order.getRestaurantEntity().getRestaurantName());
        }
        if (order.getDeliveryAgent() != null) {
            dto.setDeliveryAgentId(order.getDeliveryAgent().getId());
            dto.setDeliveryAgentName(order.getDeliveryAgent().getFullName());
        }
        if (order.getOrderItemEntities() != null) {
            dto.setOrderItemIds(order.getOrderItemEntities().stream()
                    .map(item -> item.getId()).collect(Collectors.toList()));
            dto.setItems(order.getOrderItemEntities().stream()
                    .map(item -> new OrderItemDetailDTO(
                            item.getId(),
                            item.getFoodItemEntity() != null ? item.getFoodItemEntity().getId() : null,
                            item.getTitle(),
                            item.getQuantity(),
                            item.getPrice()))
                    .collect(Collectors.toList()));
        }
        return dto;
    }
}
