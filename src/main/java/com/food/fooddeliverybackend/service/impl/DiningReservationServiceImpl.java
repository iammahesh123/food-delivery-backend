package com.food.fooddeliverybackend.service.impl;

import com.food.fooddeliverybackend.entity.DiningReservationEntity;
import com.food.fooddeliverybackend.entity.RestaurantEntity;
import com.food.fooddeliverybackend.entity.UserEntity;
import com.food.fooddeliverybackend.enums.DiningOccasion;
import com.food.fooddeliverybackend.enums.ReservationStatus;
import com.food.fooddeliverybackend.enums.SeatingArea;
import com.food.fooddeliverybackend.exception.ResourceNotFoundException;
import com.food.fooddeliverybackend.model.DiningReservationRequestDto;
import com.food.fooddeliverybackend.model.DiningReservationResponseDto;
import com.food.fooddeliverybackend.model.ReservationStatusUpdateDto;
import com.food.fooddeliverybackend.repository.DiningReservationRepository;
import com.food.fooddeliverybackend.repository.RestaurantRepository;
import com.food.fooddeliverybackend.repository.UserRepository;
import com.food.fooddeliverybackend.service.DiningReservationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class DiningReservationServiceImpl implements DiningReservationService {

    private final DiningReservationRepository diningReservationRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public DiningReservationServiceImpl(DiningReservationRepository diningReservationRepository,
                                       RestaurantRepository restaurantRepository,
                                       UserRepository userRepository) {
        this.diningReservationRepository = diningReservationRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @Override
    public DiningReservationResponseDto createReservation(DiningReservationRequestDto requestDto) {
        RestaurantEntity restaurant = restaurantRepository.findById(requestDto.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + requestDto.getRestaurantId()));

        DiningReservationEntity reservation = new DiningReservationEntity();
        reservation.setRestaurantEntity(restaurant);

        if (requestDto.getUserId() != null) {
            userRepository.findById(requestDto.getUserId()).ifPresent(reservation::setUserEntity);
        }

        reservation.setBookingDate(requestDto.getBookingDate());
        reservation.setBookingTime(requestDto.getBookingTime());
        reservation.setGuestCount(requestDto.getGuestCount() > 0 ? requestDto.getGuestCount() : 2);
        reservation.setMealType(requestDto.getMealType() != null ? requestDto.getMealType() : "DINNER");
        reservation.setSeatingArea(requestDto.getSeatingArea() != null ? requestDto.getSeatingArea() : SeatingArea.MAIN_DINING);
        reservation.setOccasion(requestDto.getOccasion() != null ? requestDto.getOccasion() : DiningOccasion.CASUAL);
        reservation.setSpecialRequests(requestDto.getSpecialRequests());
        reservation.setGuestName(requestDto.getGuestName() != null ? requestDto.getGuestName() : "Valued Guest");
        reservation.setGuestPhone(requestDto.getGuestPhone() != null ? requestDto.getGuestPhone() : "N/A");
        reservation.setGuestEmail(requestDto.getGuestEmail());
        reservation.setDiscountPercent(requestDto.getDiscountPercent());
        reservation.setStatus(ReservationStatus.CONFIRMED);

        // Generate short human-readable booking reference e.g. TOM-DINE-A3B8
        String ref = "TOM-DINE-" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        reservation.setBookingReference(ref);

        DiningReservationEntity saved = diningReservationRepository.save(reservation);
        return mapToDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public DiningReservationResponseDto getReservationById(Long id) {
        DiningReservationEntity reservation = diningReservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + id));
        return mapToDto(reservation);
    }

    @Override
    @Transactional(readOnly = true)
    public DiningReservationResponseDto getReservationByReference(String bookingReference) {
        DiningReservationEntity reservation = diningReservationRepository.findByBookingReference(bookingReference)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with reference: " + bookingReference));
        return mapToDto(reservation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiningReservationResponseDto> getReservationsByUser(Long userId) {
        return diningReservationRepository.findByUserEntityIdOrderByBookingDateDesc(userId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiningReservationResponseDto> getReservationsByRestaurant(Long restaurantId) {
        return diningReservationRepository.findByRestaurantEntityIdOrderByBookingDateDesc(restaurantId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiningReservationResponseDto> getReservationsByRestaurantAndDate(Long restaurantId, LocalDate date) {
        return diningReservationRepository.findByRestaurantEntityIdAndBookingDate(restaurantId, date)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DiningReservationResponseDto updateReservationStatus(Long id, ReservationStatusUpdateDto updateDto) {
        DiningReservationEntity reservation = diningReservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + id));

        if (updateDto.getStatus() != null) {
            reservation.setStatus(updateDto.getStatus());
        }
        if (updateDto.getTableNumber() != null && !updateDto.getTableNumber().isBlank()) {
            reservation.setTableNumber(updateDto.getTableNumber().trim());
        }
        if (updateDto.getBillAmount() != null) {
            reservation.setBillAmount(updateDto.getBillAmount());
        }

        DiningReservationEntity updated = diningReservationRepository.save(reservation);
        return mapToDto(updated);
    }

    @Override
    public DiningReservationResponseDto cancelReservation(Long id) {
        DiningReservationEntity reservation = diningReservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + id));

        reservation.setStatus(ReservationStatus.CANCELLED);
        DiningReservationEntity updated = diningReservationRepository.save(reservation);
        return mapToDto(updated);
    }

    @Override
    public DiningReservationResponseDto settleBill(Long id, Double billAmount) {
        DiningReservationEntity reservation = diningReservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + id));

        reservation.setBillAmount(billAmount);
        reservation.setStatus(ReservationStatus.COMPLETED);
        DiningReservationEntity updated = diningReservationRepository.save(reservation);
        return mapToDto(updated);
    }

    private DiningReservationResponseDto mapToDto(DiningReservationEntity entity) {
        DiningReservationResponseDto dto = new DiningReservationResponseDto();
        dto.setId(entity.getId());
        dto.setBookingReference(entity.getBookingReference());
        if (entity.getRestaurantEntity() != null) {
            dto.setRestaurantId(entity.getRestaurantEntity().getId());
            dto.setRestaurantName(entity.getRestaurantEntity().getRestaurantName());
            dto.setRestaurantAddress(entity.getRestaurantEntity().getAddress());
            dto.setRestaurantImageUrl(entity.getRestaurantEntity().getImageUrl());
        }
        if (entity.getUserEntity() != null) {
            dto.setUserId(entity.getUserEntity().getId());
        }
        dto.setBookingDate(entity.getBookingDate());
        dto.setBookingTime(entity.getBookingTime());
        dto.setGuestCount(entity.getGuestCount());
        dto.setMealType(entity.getMealType());
        dto.setSeatingArea(entity.getSeatingArea());
        dto.setOccasion(entity.getOccasion());
        dto.setSpecialRequests(entity.getSpecialRequests());
        dto.setGuestName(entity.getGuestName());
        dto.setGuestPhone(entity.getGuestPhone());
        dto.setGuestEmail(entity.getGuestEmail());
        dto.setStatus(entity.getStatus());
        dto.setTableNumber(entity.getTableNumber());
        dto.setDiscountPercent(entity.getDiscountPercent());
        dto.setBillAmount(entity.getBillAmount());
        dto.setCreatedAt(entity.getCreatedAt() != null ? entity.getCreatedAt() : LocalDateTime.now());
        return dto;
    }
}
