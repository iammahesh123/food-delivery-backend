package com.food.fooddeliverybackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventBookingRequestDto {
    private Long eventId;
    private Long userId;
    private String ticketTier;
    private int quantity;
    private double unitPrice;
    private double totalAmount;
    private String attendeeName;
    private String attendeePhone;
    private String attendeeEmail;
    private boolean foodVoucherIncluded;
}
