package com.food.fooddeliverybackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class UserResponseDTO {
    String token;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public UserResponseDTO(String token, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.token = token;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
