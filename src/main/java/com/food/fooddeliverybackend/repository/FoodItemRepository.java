package com.food.fooddeliverybackend.repository;

import com.food.fooddeliverybackend.entity.FoodItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItemEntity, Long> {
    List<FoodItemEntity> findByRestaurantEntity_Id(Long restaurantId);
}
