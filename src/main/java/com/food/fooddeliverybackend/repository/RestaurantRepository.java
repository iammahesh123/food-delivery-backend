package com.food.fooddeliverybackend.repository;

import com.food.fooddeliverybackend.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
    List<RestaurantEntity> findByOwner_Id(Long ownerId);
}
