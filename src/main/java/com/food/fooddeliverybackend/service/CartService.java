package com.food.fooddeliverybackend.service;

import com.food.fooddeliverybackend.model.CartRequestDTO;
import com.food.fooddeliverybackend.model.CartResponseDTO;
import com.food.fooddeliverybackend.model.PageModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {
    CartResponseDTO cartCreate(CartRequestDTO cartRequestDTO);
    CartResponseDTO cartUpdate(Long id,CartRequestDTO cartRequestDTO);
    CartResponseDTO getCartById(Long id);
    List<CartResponseDTO> getAllCart(PageModel pageModel);
    void deleteCartById(Long id);
}
