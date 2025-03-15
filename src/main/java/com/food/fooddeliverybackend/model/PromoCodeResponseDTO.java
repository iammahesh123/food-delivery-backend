package com.food.fooddeliverybackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromoCodeResponseDTO {
    private Long id;
    private String codeName;
    private String code;
    private double discountAmount;
    private LocalDateTime validFrom;
    private LocalDateTime validUntil;
    private int usageLimit;
    private int usedCount=0;
    private boolean isActive=true;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
