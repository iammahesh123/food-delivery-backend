package com.food.fooddeliverybackend.repository;

import com.food.fooddeliverybackend.entity.PromoCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromoCodeRepository extends JpaRepository<PromoCodeEntity, Long> {
}
