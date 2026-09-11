package com.food.fooddeliverybackend.entity;

import com.food.fooddeliverybackend.audit.BaseEntity;
import com.food.fooddeliverybackend.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "event_tickets")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventTicketEntity extends BaseEntity<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id", nullable = false)
    private LiveEventEntity eventEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Column(nullable = false)
    private String ticketTier; // e.g. "General Entry", "VIP Pass", "VIP Table for 4"

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private double unitPrice;

    @Column(nullable = false)
    private double totalAmount;

    @Column(nullable = false, unique = true)
    private String bookingReference; // e.g. "TOM-EVT-7721"

    @Column(nullable = false)
    private String attendeeName;

    @Column(nullable = false)
    private String attendeePhone;

    private String attendeeEmail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus status = TicketStatus.CONFIRMED;

    private boolean foodVoucherIncluded = false;

    private String qrCode;

    private LocalDateTime purchaseDate = LocalDateTime.now();
}
