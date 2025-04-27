package com.food.fooddeliverybackend.entity;

import com.food.fooddeliverybackend.audit.BaseEntity;
import com.food.fooddeliverybackend.enums.Amenities;
import com.food.fooddeliverybackend.enums.CuisineType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantEntity extends BaseEntity<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String restaurantName;
    private String description;
    private String address;
    private String phone;
    private int totalReviews;

    @ElementCollection
    @CollectionTable(name = "restaurant_cuisines", joinColumns = @JoinColumn(name = "restaurant_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "cuisine_type")
    private List<CuisineType> cuisineTypes = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "restaurant_amentities", joinColumns = @JoinColumn(name = "restaurant_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "amenities")
    private Set<Amenities> amenities = new HashSet<>();

    private LocalTime openingTime;
    private LocalTime closingTime;
    private double rating;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private UserEntity owner;

    @OneToMany(mappedBy = "restaurantEntity", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<FoodItemEntity> foodItemEntities;

    @OneToMany(mappedBy = "restaurantEntity", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<OrdersEntity> orders;

    @ManyToOne
    @JoinColumn(name = "collection_id")
    private CollectionEntity collectionEntity;

    @OneToMany(mappedBy = "restaurantEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ReviewEntity> reviewEntities = new ArrayList<>();

    @Transient
    public int getTotalReviews() {
        return reviewEntities != null ? reviewEntities.size(): 0;
    }
}
