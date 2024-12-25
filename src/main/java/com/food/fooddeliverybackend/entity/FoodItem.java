package com.food.fooddeliverybackend.entity;

import com.food.fooddeliverybackend.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodItem extends BaseEntity<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;

    @Lob // Use if storing the image in the database
    private byte[] image;
    private String category;

}
