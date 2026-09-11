package com.food.fooddeliverybackend.repository;

import com.food.fooddeliverybackend.entity.EventTicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventTicketRepository extends JpaRepository<EventTicketEntity, Long> {

    List<EventTicketEntity> findByUserEntityIdOrderByPurchaseDateDesc(Long userId);

    List<EventTicketEntity> findByEventEntityIdOrderByPurchaseDateDesc(Long eventId);

    Optional<EventTicketEntity> findByBookingReference(String bookingReference);
}
