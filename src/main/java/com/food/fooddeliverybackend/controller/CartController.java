package com.food.fooddeliverybackend.controller;

import com.food.fooddeliverybackend.enums.OrderBy;
import com.food.fooddeliverybackend.model.CartRequestDTO;
import com.food.fooddeliverybackend.model.CartResponseDTO;
import com.food.fooddeliverybackend.model.PageModel;
import com.food.fooddeliverybackend.service.CartService;
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

    @PostMapping
    public ResponseEntity<CartResponseDTO> create(@RequestBody CartRequestDTO cartRequestDTO) {
        return ResponseEntity.ok(cartService.cartCreate(cartRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartResponseDTO> update(@PathVariable("id") Long id, @RequestBody CartRequestDTO cartRequestDTO) {
        return ResponseEntity.ok(cartService.cartUpdate(id, cartRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartResponseDTO> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(cartService.getCartById(id));
    }

    @GetMapping
    public ResponseEntity<List<CartResponseDTO>> getAll(
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "sortColumn", required = false) String sortColumn,
            @RequestParam(value = "orderBY", required = false) OrderBy orderBy) {
        PageModel pageModel = new PageModel(pageNumber, pageSize, sortColumn, orderBy);
        return ResponseEntity.ok(cartService.getAllCart(pageModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        cartService.deleteCartById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
