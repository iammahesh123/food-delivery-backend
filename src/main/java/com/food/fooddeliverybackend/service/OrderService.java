package com.food.fooddeliverybackend.service;

import com.food.fooddeliverybackend.model.OrderRequestDTO;
import com.food.fooddeliverybackend.model.OrderResponseDTO;
import com.food.fooddeliverybackend.model.PageModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO);
    OrderResponseDTO updateOrder(Long id,OrderRequestDTO orderRequestDTO);
    OrderResponseDTO getOrderById(Long id);
    List<OrderResponseDTO> getAllOrders(PageModel pageModel);
    void deleteOrder(Long id);
}
