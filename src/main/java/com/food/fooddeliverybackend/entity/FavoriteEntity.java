package com.food.fooddeliverybackend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class FavoriteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long targetId; // either food item or restaurant
    private String type; // "FOOD", "RESTAURANT"

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}

