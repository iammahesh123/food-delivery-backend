package com.food.fooddeliverybackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodItemRequestDTO {
    private String name;
    private String description;
    private Double price;
    private String category;
    private String imageUrl;
}
