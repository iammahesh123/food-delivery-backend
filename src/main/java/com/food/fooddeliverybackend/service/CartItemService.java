package com.food.fooddeliverybackend.service;

import com.food.fooddeliverybackend.model.CartItemRequestDTO;
import com.food.fooddeliverybackend.model.CartItemResponseDTO;
import com.food.fooddeliverybackend.model.PageModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartItemService {
    CartItemResponseDTO createCartItem(CartItemRequestDTO cartItemRequestDTO);
    CartItemResponseDTO update(Long id, CartItemRequestDTO cartItemRequestDTO);
    CartItemResponseDTO getCartItemById(Long id);
    List<CartItemResponseDTO> getAllCartItems(PageModel pageModel);
    void deleteCartItemById(Long id);
}
