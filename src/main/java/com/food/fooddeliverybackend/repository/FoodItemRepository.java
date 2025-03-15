package com.food.fooddeliverybackend.repository;

import com.food.fooddeliverybackend.entity.FoodItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItemEntity, Long> {
}
