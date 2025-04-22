package com.food.fooddeliverybackend.controller;

import com.food.fooddeliverybackend.enums.OrderBy;
import com.food.fooddeliverybackend.model.CartItemRequestDTO;
import com.food.fooddeliverybackend.model.CartItemResponseDTO;
import com.food.fooddeliverybackend.model.PageModel;
import com.food.fooddeliverybackend.service.CartItemService;
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

    @PostMapping
    public ResponseEntity<CartItemResponseDTO> createCartItem(@RequestBody CartItemRequestDTO cartItemRequestDTO) {
        return ResponseEntity.ok(cartItemService.createCartItem(cartItemRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartItemResponseDTO> updateCartItem(@PathVariable Long id, @RequestBody CartItemRequestDTO cartItemRequestDTO) {
        return ResponseEntity.ok(cartItemService.update(id, cartItemRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartItemResponseDTO> getCartItem(@PathVariable Long id) {
        return ResponseEntity.ok(cartItemService.getCartItemById(id));
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponseDTO>> getCartItems(
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "sortColumn", required = false) String sortColumn,
            @RequestParam(value = "orderBY", required = false) OrderBy orderBy) {
        PageModel pageModel = new PageModel(pageNumber, pageSize, sortColumn, orderBy);
        return ResponseEntity.ok(cartItemService.getAllCartItems(pageModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCartItem(@PathVariable Long id) {
        cartItemService.deleteCartItemById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
