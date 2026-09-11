package com.food.fooddeliverybackend.repository;

import com.food.fooddeliverybackend.entity.DiningReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiningReservationRepository extends JpaRepository<DiningReservationEntity, Long> {

    List<DiningReservationEntity> findByUserEntityIdOrderByBookingDateDesc(Long userId);

    List<DiningReservationEntity> findByRestaurantEntityIdOrderByBookingDateDesc(Long restaurantId);

    List<DiningReservationEntity> findByRestaurantEntityIdAndBookingDate(Long restaurantId, LocalDate bookingDate);

    Optional<DiningReservationEntity> findByBookingReference(String bookingReference);
}
