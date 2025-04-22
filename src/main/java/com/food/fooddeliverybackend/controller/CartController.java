package com.food.fooddeliverybackend.controller;

import com.food.fooddeliverybackend.enums.OrderBy;
import com.food.fooddeliverybackend.model.CartRequestDTO;
import com.food.fooddeliverybackend.model.CartResponseDTO;
import com.food.fooddeliverybackend.model.PageModel;
import com.food.fooddeliverybackend.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Cart Controller", description = "List of Cart Endpoints")
@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @Operation(summary = "Create a new cart item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart item created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping
    public ResponseEntity<CartResponseDTO> create(
            @RequestBody CartRequestDTO cartRequestDTO) {
        return ResponseEntity.ok(cartService.cartCreate(cartRequestDTO));
    }

    @Operation(summary = "Update an existing cart item by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart item updated successfully"),
            @ApiResponse(responseCode = "404", description = "Cart item not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CartResponseDTO> update(
            @PathVariable("id") Long id,
            @RequestBody CartRequestDTO cartRequestDTO) {
        return ResponseEntity.ok(cartService.cartUpdate(id, cartRequestDTO));
    }

    @Operation(summary = "Get a cart item by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart item fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Cart item not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CartResponseDTO> get(
            @PathVariable("id") Long id) {
        return ResponseEntity.ok(cartService.getCartById(id));
    }

    @Operation(summary = "Get all cart items with pagination and sorting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart items fetched successfully")
    })
    @GetMapping
    public ResponseEntity<List<CartResponseDTO>> getAll(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @Parameter(description = "Sort column", example = "createdAt")
            @RequestParam(value = "sortColumn", required = false) String sortColumn,
            @Parameter(description = "Order by ASC or DESC", example = "ASC")
            @RequestParam(value = "orderBY", required = false) OrderBy orderBy) {

        PageModel pageModel = new PageModel(pageNumber, pageSize, sortColumn, orderBy);
        return ResponseEntity.ok(cartService.getAllCart(pageModel));
    }

    @Operation(summary = "Delete a cart item by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart item deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Cart item not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(
            @PathVariable("id") Long id) {
        cartService.deleteCartById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
