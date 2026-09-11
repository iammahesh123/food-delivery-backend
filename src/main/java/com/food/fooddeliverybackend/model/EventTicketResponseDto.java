package com.food.fooddeliverybackend.model;

import com.food.fooddeliverybackend.enums.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventTicketResponseDto {
    private Long id;
    private Long eventId;
    private String eventTitle;
    private String eventArtist;
    private String eventVenue;
    private String eventAddress;
    private LocalDate eventDate;
    private LocalTime eventStartTime;
    private String eventImageUrl;
    private Long userId;
    private String ticketTier;
    private int quantity;
    private double unitPrice;
    private double totalAmount;
    private String bookingReference;
    private String attendeeName;
    private String attendeePhone;
    private String attendeeEmail;
    private TicketStatus status;
    private boolean foodVoucherIncluded;
    private String qrCode;
    private LocalDateTime purchaseDate;
}
