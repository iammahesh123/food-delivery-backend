package com.food.fooddeliverybackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequestDTO {
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String landmark;
    private long userId;
}
