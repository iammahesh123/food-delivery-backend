package com.food.fooddeliverybackend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Dashboard Controller", description = "List of Dashboard Endpoints")
@RestController
public class DashboardController {

    @GetMapping
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Welcome to FoodDelivery Backend");
    }
}
