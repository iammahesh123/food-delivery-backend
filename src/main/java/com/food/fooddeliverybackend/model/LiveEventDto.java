package com.food.fooddeliverybackend.model;

import com.food.fooddeliverybackend.enums.EventCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LiveEventDto {
    private Long id;
    private String title;
    private String tagline;
    private String description;
    private EventCategory category;
    private String artistName;
    private String venueName;
    private String venueAddress;
    private LocalDate eventDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String imageUrl;
    private String bannerUrl;
    private double minPrice;
    private int totalCapacity;
    private int availableTickets;
    private boolean featured;
}
