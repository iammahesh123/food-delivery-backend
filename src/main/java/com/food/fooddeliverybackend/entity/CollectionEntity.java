package com.food.fooddeliverybackend.entity;

import com.food.fooddeliverybackend.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectionEntity extends BaseEntity<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String collectionName;
    private String collectionType;
    private String description;
    private long places;

    @OneToMany(mappedBy = "collectionEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<RestaurantEntity> restaurantEntities = new ArrayList<>();
}
