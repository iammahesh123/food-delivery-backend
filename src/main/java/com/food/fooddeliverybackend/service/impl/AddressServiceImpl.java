package com.food.fooddeliverybackend.service.impl;

import com.food.fooddeliverybackend.entity.AddressEntity;
import com.food.fooddeliverybackend.mapper.AddressMapper;
import com.food.fooddeliverybackend.model.AddressRequestDTO;
import com.food.fooddeliverybackend.model.AddressResponseDTO;
import com.food.fooddeliverybackend.model.PageModel;
import com.food.fooddeliverybackend.repository.AddressRepository;
import com.food.fooddeliverybackend.service.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.food.fooddeliverybackend.util.PaginationUtility.applyPagination;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final ModelMapper modelMapper;

    public AddressServiceImpl(AddressRepository addressRepository, AddressMapper addressMapper, ModelMapper modelMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public AddressResponseDTO createAddress(AddressRequestDTO addressRequestDTO) {
        AddressEntity addressEntity = new AddressEntity();
        BeanUtils.copyProperties(addressRequestDTO, addressEntity);
        AddressEntity savedAddress = addressRepository.save(addressEntity);
        return addressMapper.toDTO(savedAddress,modelMapper);
    }

    @Override
    public AddressResponseDTO updateAddress(Long id, AddressRequestDTO addressRequestDTO) {
        AddressEntity existingAddress = addressRepository.findById(id).orElse(null);
        BeanUtils.copyProperties(addressRequestDTO, existingAddress);
        AddressEntity updatedAddress = addressRepository.save(existingAddress);
        return addressMapper.toDTO(updatedAddress,modelMapper);
    }

    @Override
    public AddressResponseDTO getAddressById(Long id) {
        AddressEntity address = addressRepository.findById(id).orElse(null);
        return addressMapper.toDTO(address,modelMapper);
    }

    @Override
    public List<AddressResponseDTO> getAllAddresses(PageModel pageModel) {
        Page<AddressEntity> addressEntities = addressRepository.findAll(applyPagination(pageModel));
        return addressEntities.stream().map(addressEntity -> addressMapper.toDTO(addressEntity,modelMapper)).collect(Collectors.toList());
    }

    @Override
    public void deleteAddress(Long id) {
        AddressEntity address = addressRepository.findById(id).orElse(null);
        addressRepository.delete(address);
    }
}
