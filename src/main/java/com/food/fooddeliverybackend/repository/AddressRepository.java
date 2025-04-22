package com.food.fooddeliverybackend.repository;

import com.food.fooddeliverybackend.entity.AddressEntity;
import com.food.fooddeliverybackend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    List<AddressEntity> findAllByUser(UserEntity user);
}
