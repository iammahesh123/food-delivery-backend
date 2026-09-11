package com.food.fooddeliverybackend.model;

import com.food.fooddeliverybackend.enums.DiningOccasion;
import com.food.fooddeliverybackend.enums.ReservationStatus;
import com.food.fooddeliverybackend.enums.SeatingArea;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiningReservationResponseDto {
    private Long id;
    private String bookingReference;
    private Long restaurantId;
    private String restaurantName;
    private String restaurantAddress;
    private String restaurantImageUrl;
    private Long userId;
    private LocalDate bookingDate;
    private LocalTime bookingTime;
    private int guestCount;
    private String mealType;
    private SeatingArea seatingArea;
    private DiningOccasion occasion;
    private String specialRequests;
    private String guestName;
    private String guestPhone;
    private String guestEmail;
    private ReservationStatus status;
    private String tableNumber;
    private double discountPercent;
    private Double billAmount;
    private LocalDateTime createdAt;
}
