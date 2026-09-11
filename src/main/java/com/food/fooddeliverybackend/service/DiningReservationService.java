package com.food.fooddeliverybackend.service;

import com.food.fooddeliverybackend.model.DiningReservationRequestDto;
import com.food.fooddeliverybackend.model.DiningReservationResponseDto;
import com.food.fooddeliverybackend.model.ReservationStatusUpdateDto;

import java.time.LocalDate;
import java.util.List;

public interface DiningReservationService {
    DiningReservationResponseDto createReservation(DiningReservationRequestDto requestDto);

    DiningReservationResponseDto getReservationById(Long id);

    DiningReservationResponseDto getReservationByReference(String bookingReference);

    List<DiningReservationResponseDto> getReservationsByUser(Long userId);

    List<DiningReservationResponseDto> getReservationsByRestaurant(Long restaurantId);

    List<DiningReservationResponseDto> getReservationsByRestaurantAndDate(Long restaurantId, LocalDate date);

    DiningReservationResponseDto updateReservationStatus(Long id, ReservationStatusUpdateDto updateDto);

    DiningReservationResponseDto cancelReservation(Long id);

    DiningReservationResponseDto settleBill(Long id, Double billAmount);
}
