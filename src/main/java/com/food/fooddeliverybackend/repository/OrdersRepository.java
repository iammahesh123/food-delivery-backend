package com.food.fooddeliverybackend.repository;


import com.food.fooddeliverybackend.entity.OrdersEntity;
import com.food.fooddeliverybackend.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<OrdersEntity, Long> {

    List<OrdersEntity> findByUserEntity_IdOrderByOrderDateDesc(Long userId);

    List<OrdersEntity> findByRestaurantEntity_IdOrderByOrderDateDesc(Long restaurantId);

    List<OrdersEntity> findByDeliveryAgent_IdOrderByOrderDateDesc(Long deliveryAgentId);

    List<OrdersEntity> findByStatus(OrderStatus status);

    List<OrdersEntity> findByStatusAndDeliveryAgentIsNull(OrderStatus status);

    long countByStatus(OrderStatus status);
}
