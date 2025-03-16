package com.food.fooddeliverybackend.controller;

import com.food.fooddeliverybackend.model.FoodItemRequestDTO;
import com.food.fooddeliverybackend.model.FoodItemResponseDTO;
import com.food.fooddeliverybackend.service.FoodItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

    @PostMapping
    public ResponseEntity<FoodItemResponseDTO> uploadFood(@ModelAttribute FoodItemRequestDTO foodRequestDTO) {
        FoodItemResponseDTO foodResponse = foodService.addFood(foodRequestDTO);
        return ResponseEntity.ok(foodResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FoodItemResponseDTO> updateFood(@PathVariable Long id, @RequestBody FoodItemRequestDTO foodRequestDTO) {
        FoodItemResponseDTO foodResponse = foodService.updateFood(id, foodRequestDTO);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteFoodById(@PathVariable Long id) {
        foodService.deleteFoodById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
