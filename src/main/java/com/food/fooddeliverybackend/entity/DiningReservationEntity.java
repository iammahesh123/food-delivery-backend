package com.food.fooddeliverybackend.entity;

import com.food.fooddeliverybackend.audit.BaseEntity;
import com.food.fooddeliverybackend.enums.DiningOccasion;
import com.food.fooddeliverybackend.enums.ReservationStatus;
import com.food.fooddeliverybackend.enums.SeatingArea;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "dining_reservations")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiningReservationEntity extends BaseEntity<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private RestaurantEntity restaurantEntity;

    @Column(nullable = false)
    private LocalDate bookingDate;

    @Column(nullable = false)
    private LocalTime bookingTime;

    @Column(nullable = false)
    private int guestCount;

    @Column(nullable = false)
    private String mealType; // LUNCH, DINNER, BRUNCH, BREAKFAST

    @Enumerated(EnumType.STRING)
    private SeatingArea seatingArea = SeatingArea.MAIN_DINING;

    @Enumerated(EnumType.STRING)
    private DiningOccasion occasion = DiningOccasion.CASUAL;

    private String specialRequests;

    @Column(nullable = false)
    private String guestName;

    @Column(nullable = false)
    private String guestPhone;

    private String guestEmail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status = ReservationStatus.CONFIRMED;

    private String tableNumber;

    private double discountPercent = 0.0;

    private Double billAmount;

    private String bookingReference;
}
