package com.food.fooddeliverybackend.repository;

import com.food.fooddeliverybackend.entity.LiveEventEntity;
import com.food.fooddeliverybackend.enums.EventCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LiveEventRepository extends JpaRepository<LiveEventEntity, Long> {

    List<LiveEventEntity> findByCategory(EventCategory category);

    List<LiveEventEntity> findByFeaturedTrue();

    List<LiveEventEntity> findByEventDateGreaterThanEqualOrderByEventDateAsc(LocalDate date);
}
