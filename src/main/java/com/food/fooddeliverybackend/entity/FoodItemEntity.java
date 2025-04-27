package com.food.fooddeliverybackend.entity;

import com.food.fooddeliverybackend.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodItemEntity extends BaseEntity<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private String category;
    private boolean isAvailable = true;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private RestaurantEntity restaurantEntity;

    @OneToMany(mappedBy = "foodItemEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ReviewEntity> reviewEntityList = new ArrayList<>();

}
