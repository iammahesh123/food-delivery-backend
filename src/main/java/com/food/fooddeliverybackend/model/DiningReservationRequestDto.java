package com.food.fooddeliverybackend.model;

import com.food.fooddeliverybackend.enums.DiningOccasion;
import com.food.fooddeliverybackend.enums.SeatingArea;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiningReservationRequestDto {
    private Long restaurantId;
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
    private double discountPercent;
}
