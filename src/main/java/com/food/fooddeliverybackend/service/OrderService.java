package com.food.fooddeliverybackend.service;

import com.food.fooddeliverybackend.enums.OrderStatus;
import com.food.fooddeliverybackend.model.OrderRequestDTO;
import com.food.fooddeliverybackend.model.OrderResponseDTO;
import com.food.fooddeliverybackend.model.PageModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface OrderService {
    OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO);
    OrderResponseDTO updateOrder(Long id,OrderRequestDTO orderRequestDTO);
    OrderResponseDTO getOrderById(Long id);
    List<OrderResponseDTO> getAllOrders(PageModel pageModel);
    void deleteOrder(Long id);

    // Customer
    List<OrderResponseDTO> getMyOrders();
    OrderResponseDTO cancelMyOrder(Long id);

    // Restaurant owner
    List<OrderResponseDTO> getOrdersByRestaurant(Long restaurantId);
    OrderResponseDTO updateOrderStatus(Long id, OrderStatus status);
    Map<String, Object> getRestaurantStats(Long restaurantId);

    // Delivery agent
    List<OrderResponseDTO> getAvailableDeliveries();
    List<OrderResponseDTO> getMyDeliveries();
    OrderResponseDTO acceptDelivery(Long orderId);
    OrderResponseDTO updateDeliveryStatus(Long orderId, OrderStatus status);
}
