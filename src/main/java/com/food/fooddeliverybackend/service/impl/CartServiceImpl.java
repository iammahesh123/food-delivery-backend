package com.food.fooddeliverybackend.service.impl;

import com.food.fooddeliverybackend.entity.CartEntity;
import com.food.fooddeliverybackend.entity.CartItemEntity;
import com.food.fooddeliverybackend.mapper.CartMapper;
import com.food.fooddeliverybackend.model.CartRequestDTO;
import com.food.fooddeliverybackend.model.CartResponseDTO;
import com.food.fooddeliverybackend.model.PageModel;
import com.food.fooddeliverybackend.repository.CartRepository;
import com.food.fooddeliverybackend.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    private final CartMapper cartMapper;
    private final ModelMapper modelMapper;
    private final CartRepository cartRepository;

    public CartServiceImpl(CartMapper cartMapper, ModelMapper modelMapper, CartRepository cartRepository) {
        this.cartMapper = cartMapper;
        this.modelMapper = modelMapper;
        this.cartRepository = cartRepository;
    }

    @Override
    public CartResponseDTO cartCreate(CartRequestDTO cartRequestDTO) {
        CartEntity cartEntity = new CartEntity();
        BeanUtils.copyProperties(cartRequestDTO, cartEntity);
        CartEntity savedCarts = cartRepository.save(cartEntity);
        return cartMapper.toDTO(savedCarts, modelMapper);
    }

    @Override
    public CartResponseDTO cartUpdate(Long id, CartRequestDTO cartRequestDTO) {
        CartEntity cart = cartRepository.findById(id).orElse(null);
        BeanUtils.copyProperties(cartRequestDTO, cart);
        CartEntity savedCart = cartRepository.save(cart);
        return cartMapper.toDTO(savedCart, modelMapper);
    }

    @Override
    public CartResponseDTO getCartById(Long id) {
        CartEntity entity = cartRepository.findById(id).orElse(null);
        return cartMapper.toDTO(entity,modelMapper);
    }

    @Override
    public List<CartResponseDTO> getAllCart(PageModel pageModel) {
        List<CartEntity> carts = cartRepository.findAll();
        return carts.stream().map(entity -> cartMapper.toDTO(entity,modelMapper)).collect(Collectors.toList());
    }

    @Override
    public void deleteCartById(Long id) {
       CartEntity cart = cartRepository.findById(id).orElse(null);
       cartRepository.delete(cart);
    }
}
