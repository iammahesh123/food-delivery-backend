package com.food.fooddeliverybackend.repository;

import com.food.fooddeliverybackend.entity.FavoriteEntity;
import com.food.fooddeliverybackend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Long> {
    List<FavoriteEntity> findAllByUser(UserEntity user);
}
