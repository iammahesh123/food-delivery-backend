package com.food.fooddeliverybackend.repository;

import com.food.fooddeliverybackend.entity.CollectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionsRepository extends JpaRepository<CollectionEntity, Long> {
}
