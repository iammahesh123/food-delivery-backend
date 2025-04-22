package com.food.fooddeliverybackend.controller;

import com.food.fooddeliverybackend.enums.OrderBy;
import com.food.fooddeliverybackend.model.PageModel;
import com.food.fooddeliverybackend.model.RestaurantRequestDTO;
import com.food.fooddeliverybackend.model.RestaurantResponseDTO;
import com.food.fooddeliverybackend.service.RestaurantService;
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
    public ResponseEntity<List<RestaurantResponseDTO>> getAllRestaurants(
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "sortColumn", required = false) String sortColumn,
            @RequestParam(value = "orderBY", required = false) OrderBy orderBy) {
        PageModel pageModel = new PageModel(pageNumber, pageSize, sortColumn, orderBy);
        return ResponseEntity.ok().body(restaurantService.getAll(pageModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteRestaurant(@PathVariable Long id) {
        restaurantService.delete(id);
        return ResponseEntity.ok().body(HttpStatus.ACCEPTED);
    }

}
