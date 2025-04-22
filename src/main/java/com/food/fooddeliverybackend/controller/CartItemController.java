package com.food.fooddeliverybackend.controller;

import com.food.fooddeliverybackend.enums.OrderBy;
import com.food.fooddeliverybackend.model.CartItemRequestDTO;
import com.food.fooddeliverybackend.model.CartItemResponseDTO;
import com.food.fooddeliverybackend.model.PageModel;
import com.food.fooddeliverybackend.service.CartItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "CartItems Controller", description = "List of CartItems Endpoints")
@RestController
@RequestMapping("/cart-items")
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @Operation(summary = "Create a new cart item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart item created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    @PostMapping
    public ResponseEntity<CartItemResponseDTO> createCartItem(
            @RequestBody CartItemRequestDTO cartItemRequestDTO) {
        return ResponseEntity.ok(cartItemService.createCartItem(cartItemRequestDTO));
    }

    @Operation(summary = "Update a cart item by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart item updated successfully"),
            @ApiResponse(responseCode = "404", description = "Cart item not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CartItemResponseDTO> updateCartItem(
            @PathVariable Long id,
            @RequestBody CartItemRequestDTO cartItemRequestDTO) {
        return ResponseEntity.ok(cartItemService.update(id, cartItemRequestDTO));
    }

    @Operation(summary = "Get a cart item by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart item retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Cart item not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CartItemResponseDTO> getCartItem(
            @PathVariable Long id) {
        return ResponseEntity.ok(cartItemService.getCartItemById(id));
    }

    @Operation(summary = "Get all cart items with pagination and sorting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart items retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<CartItemResponseDTO>> getCartItems(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @Parameter(description = "Field to sort by", example = "createdAt")
            @RequestParam(value = "sortColumn", required = false) String sortColumn,
            @Parameter(description = "Sort direction", example = "ASC")
            @RequestParam(value = "orderBY", required = false) OrderBy orderBy) {

        PageModel pageModel = new PageModel(pageNumber, pageSize, sortColumn, orderBy);
        return ResponseEntity.ok(cartItemService.getAllCartItems(pageModel));
    }

    @Operation(summary = "Delete a cart item by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart item deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Cart item not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCartItem(
            @PathVariable Long id) {
        cartItemService.deleteCartItemById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
