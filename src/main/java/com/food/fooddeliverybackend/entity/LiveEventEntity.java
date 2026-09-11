package com.food.fooddeliverybackend.entity;

import com.food.fooddeliverybackend.audit.BaseEntity;
import com.food.fooddeliverybackend.enums.EventCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "live_events")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LiveEventEntity extends BaseEntity<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String tagline;

    @Column(length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventCategory category;

    private String artistName;

    @Column(nullable = false)
    private String venueName;

    @Column(nullable = false)
    private String venueAddress;

    @Column(nullable = false)
    private LocalDate eventDate;

    @Column(nullable = false)
    private LocalTime startTime;

    private LocalTime endTime;

    private String imageUrl;

    private String bannerUrl;

    private double minPrice;

    private int totalCapacity;

    private int availableTickets;

    private boolean featured = false;

    @OneToMany(mappedBy = "eventEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EventTicketEntity> tickets = new ArrayList<>();
}
