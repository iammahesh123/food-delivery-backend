package com.food.fooddeliverybackend.service.impl;

import com.food.fooddeliverybackend.entity.CartItemEntity;
import com.food.fooddeliverybackend.mapper.CartItemMapper;
import com.food.fooddeliverybackend.model.CartItemRequestDTO;
import com.food.fooddeliverybackend.model.CartItemResponseDTO;
import com.food.fooddeliverybackend.model.PageModel;
import com.food.fooddeliverybackend.repository.CartItemRepository;
import com.food.fooddeliverybackend.service.CartItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;
    private final ModelMapper modelMapper;

    public CartItemServiceImpl(CartItemRepository cartItemRepository, CartItemMapper cartItemMapper, ModelMapper modelMapper) {
        this.cartItemRepository = cartItemRepository;
        this.cartItemMapper = cartItemMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public CartItemResponseDTO createCartItem(CartItemRequestDTO cartItemRequestDTO) {
        CartItemEntity cartItemEntity = new CartItemEntity();
        BeanUtils.copyProperties(cartItemRequestDTO, cartItemEntity);
        CartItemEntity savedEntity = cartItemRepository.save(cartItemEntity);
        return cartItemMapper.toDTO(savedEntity,modelMapper);
    }

    @Override
    public CartItemResponseDTO update(Long id, CartItemRequestDTO cartItemRequestDTO) {
        CartItemEntity existingEntity = cartItemRepository.findById(id).orElse(null);
        BeanUtils.copyProperties(cartItemRequestDTO, existingEntity);
        CartItemEntity updatedCartItems = cartItemRepository.save(existingEntity);
        return cartItemMapper.toDTO(updatedCartItems,modelMapper);
    }

    @Override
    public CartItemResponseDTO getCartItemById(Long id) {
        CartItemEntity cartItemEntity = cartItemRepository.findById(id).orElse(null);
        return cartItemMapper.toDTO(cartItemEntity,modelMapper);
    }

    @Override
    public List<CartItemResponseDTO> getAllCartItems(PageModel pageModel) {
        List<CartItemEntity> cartItemEntities = cartItemRepository.findAll();
        return cartItemEntities.stream().map(item -> cartItemMapper.toDTO(item,modelMapper)).collect(Collectors.toList());
    }

    @Override
    public void deleteCartItemById(Long id) {
        CartItemEntity cartItemEntity = cartItemRepository.findById(id).orElse(null);
        cartItemRepository.delete(cartItemEntity);
    }
}
