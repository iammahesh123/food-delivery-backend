package com.food.fooddeliverybackend.controller;

import com.food.fooddeliverybackend.model.FoodItemRequestDTO;
import com.food.fooddeliverybackend.model.FoodItemResponseDTO;
import com.food.fooddeliverybackend.service.FoodItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "FoodItem Controller", description = "List of FoodItem Endpoints")
@RestController
@RequestMapping("/api/foods")
public class FoodItemController {

    private final FoodItemService foodService;

    public FoodItemController(FoodItemService foodService) {
        this.foodService = foodService;
    }

    @PostMapping("/upload")
    public ResponseEntity<FoodItemResponseDTO> uploadFood(@ModelAttribute FoodItemRequestDTO foodRequestDTO) {
        FoodItemResponseDTO foodResponse = foodService.addFood(foodRequestDTO);
        return ResponseEntity.ok(foodResponse);
    }

    @GetMapping
    public ResponseEntity<List<FoodItemResponseDTO>> getAllFoods() {
        return ResponseEntity.ok(foodService.getAllFoods());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodItemResponseDTO> getFoodById(@PathVariable Long id) {
        return ResponseEntity.ok(foodService.getFoodById(id));
    }

    @GetMapping("/images/{id}")
    public ResponseEntity<byte[]> getFoodImage(@PathVariable Long id) {
        byte[] image = foodService.getFoodImageById(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                .body(image);
    }
}
