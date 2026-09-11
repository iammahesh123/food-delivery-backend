package com.food.fooddeliverybackend.model;

import com.food.fooddeliverybackend.enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationStatusUpdateDto {
    private ReservationStatus status;
    private String tableNumber;
    private Double billAmount;
}
