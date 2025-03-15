package com.food.fooddeliverybackend.controller;

import com.food.fooddeliverybackend.model.RestaurantRequestDTO;
import com.food.fooddeliverybackend.model.RestaurantResponseDTO;
import com.food.fooddeliverybackend.service.RestaurantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping
    public ResponseEntity<RestaurantResponseDTO> createRestaurant(@RequestBody RestaurantRequestDTO restaurantRequestDTO) {
        return ResponseEntity.ok().body(restaurantService.create(restaurantRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantResponseDTO> updateRestaurant(@PathVariable Long id, @RequestBody RestaurantRequestDTO restaurantRequestDTO) {
        return ResponseEntity.ok().body(restaurantService.update(id, restaurantRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponseDTO> getRestaurant(@PathVariable Long id) {
        return ResponseEntity.ok().body(restaurantService.get(id));
    }

    @GetMapping
    public ResponseEntity<List<RestaurantResponseDTO>> getAllRestaurants() {
        return ResponseEntity.ok().body(restaurantService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestaurantResponseDTO> deleteRestaurant(@PathVariable Long id) {
        return ResponseEntity.ok().body(restaurantService.delete(id));
    }

}
