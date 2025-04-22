package com.food.fooddeliverybackend.controller;

import com.food.fooddeliverybackend.enums.OrderBy;
import com.food.fooddeliverybackend.model.FoodItemRequestDTO;
import com.food.fooddeliverybackend.model.FoodItemResponseDTO;
import com.food.fooddeliverybackend.model.PageModel;
import com.food.fooddeliverybackend.service.FoodItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
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

    @Operation(summary = "Upload a new food item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Food item uploaded successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping
    public ResponseEntity<FoodItemResponseDTO> uploadFood(
            @ModelAttribute FoodItemRequestDTO foodRequestDTO) {
        FoodItemResponseDTO foodResponse = foodService.addFood(foodRequestDTO);
        return ResponseEntity.ok(foodResponse);
    }

    @Operation(summary = "Update an existing food item by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Food item updated successfully"),
            @ApiResponse(responseCode = "404", description = "Food item not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<FoodItemResponseDTO> updateFood(
            @PathVariable Long id,
            @RequestBody FoodItemRequestDTO foodRequestDTO) {
        FoodItemResponseDTO foodResponse = foodService.updateFood(id, foodRequestDTO);
        return ResponseEntity.ok(foodResponse);
    }

    @Operation(summary = "Get a list of all food items with pagination and sorting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of food items retrieved")
    })
    @GetMapping
    public ResponseEntity<List<FoodItemResponseDTO>> getAllFoods(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @Parameter(description = "Column to sort by", example = "price")
            @RequestParam(value = "sortColumn", required = false) String sortColumn,
            @Parameter(description = "Order direction (ASC or DESC)", example = "ASC")
            @RequestParam(value = "orderBY", required = false) OrderBy orderBy) {

        PageModel pageModel = new PageModel(pageNumber, pageSize, sortColumn, orderBy);
        return ResponseEntity.ok(foodService.getAllFoods(pageModel));
    }

    @Operation(summary = "Get a food item by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Food item retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Food item not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<FoodItemResponseDTO> getFoodById(
            @PathVariable Long id) {
        return ResponseEntity.ok(foodService.getFoodById(id));
    }

    @Operation(summary = "Delete a food item by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Food item deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Food item not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteFoodById(
            @PathVariable Long id) {
        foodService.deleteFoodById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
