package com.food.fooddeliverybackend.service;

import com.food.fooddeliverybackend.model.AddressRequestDTO;
import com.food.fooddeliverybackend.model.AddressResponseDTO;
import com.food.fooddeliverybackend.model.PageModel;

import java.util.List;

public interface AddressService {
    AddressResponseDTO createAddress(AddressRequestDTO addressRequestDTO);
    AddressResponseDTO updateAddress(Long id,AddressRequestDTO addressRequestDTO);
    AddressResponseDTO getAddressById(Long id);
    List<AddressResponseDTO> getAllAddresses(PageModel pageModel);
    void deleteAddress(Long id);
}
