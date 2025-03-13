package com.food.fooddeliverybackend.entity;

import com.food.fooddeliverybackend.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CartItemEntity extends BaseEntity<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private int quantity;
    private double price;

    @ManyToOne
    @JoinColumn(name = "cart_id",nullable = false)
    private CartEntity cartEntity;

    @ManyToOne
    @JoinColumn(name = "food_item_id",nullable = false)
    private FoodItemEntity foodItemEntity;
}
