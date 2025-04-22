package com.food.fooddeliverybackend.entity;

import com.food.fooddeliverybackend.audit.BaseEntity;
import com.food.fooddeliverybackend.enums.UserRoles;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity extends BaseEntity<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRoles role = UserRoles.CUSTOMER;

    @Column(nullable = false)
    private String password;

    private boolean isActive = true;
    //private String address;
    private String profilePictureUrl;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<AddressEntity> addresses = new ArrayList<>();
}
