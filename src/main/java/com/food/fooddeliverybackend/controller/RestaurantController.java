package com.food.fooddeliverybackend.controller;

import com.food.fooddeliverybackend.enums.OrderBy;
import com.food.fooddeliverybackend.model.PageModel;
import com.food.fooddeliverybackend.model.RestaurantRequestDTO;
import com.food.fooddeliverybackend.model.RestaurantResponseDTO;
import com.food.fooddeliverybackend.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Restaurant Controller", description = "List of Restaurant Endpoints")
@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @Operation(summary = "Create a new restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurant created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid restaurant data")
    })
    @PostMapping
    public ResponseEntity<RestaurantResponseDTO> createRestaurant(
            @RequestBody RestaurantRequestDTO restaurantRequestDTO) {
        return ResponseEntity.ok().body(restaurantService.create(restaurantRequestDTO));
    }

    @Operation(summary = "Update an existing restaurant by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurant updated successfully"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<RestaurantResponseDTO> updateRestaurant(
            @PathVariable Long id,
            @RequestBody RestaurantRequestDTO restaurantRequestDTO) {
        return ResponseEntity.ok().body(restaurantService.update(id, restaurantRequestDTO));
    }

    @Operation(summary = "Get a restaurant by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurant retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponseDTO> getRestaurant(
            @PathVariable Long id) {
        return ResponseEntity.ok().body(restaurantService.get(id));
    }

    @Operation(summary = "Get all restaurants with pagination and sorting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurants retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<RestaurantResponseDTO>> getAllRestaurants(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @Parameter(description = "Sort by column", example = "restaurantName")
            @RequestParam(value = "sortColumn", required = false) String sortColumn,
            @Parameter(description = "Sort order", example = "ASC")
            @RequestParam(value = "orderBY", required = false) OrderBy orderBy) {

        PageModel pageModel = new PageModel(pageNumber, pageSize, sortColumn, orderBy);
        return ResponseEntity.ok().body(restaurantService.getAll(pageModel));
    }

    @Operation(summary = "Delete a restaurant by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Restaurant deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteRestaurant(@PathVariable Long id) {
        restaurantService.delete(id);
        return ResponseEntity.ok().body(HttpStatus.ACCEPTED);
    }
}
