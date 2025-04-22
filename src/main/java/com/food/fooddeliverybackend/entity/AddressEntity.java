package com.food.fooddeliverybackend.entity;

import com.food.fooddeliverybackend.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class AddressEntity extends BaseEntity<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String landmark;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}

