package com.food.fooddeliverybackend.service.impl;

import com.food.fooddeliverybackend.entity.CollectionEntity;
import com.food.fooddeliverybackend.mapper.CollectionsMapper;
import com.food.fooddeliverybackend.model.CollectionRequestDTO;
import com.food.fooddeliverybackend.model.CollectionResponseDTO;
import com.food.fooddeliverybackend.repository.CollectionsRepository;
import com.food.fooddeliverybackend.service.CollectionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CollectionServiceImpl implements CollectionService {
    private final CollectionsRepository collectionsRepository;
    private final CollectionsMapper collectionsMapper;
    private final ModelMapper modelMapper;

    public CollectionServiceImpl(CollectionsRepository collectionsRepository, CollectionsMapper collectionsMapper, ModelMapper modelMapper) {
        this.collectionsRepository = collectionsRepository;
        this.collectionsMapper = collectionsMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public CollectionResponseDTO createCollection(CollectionRequestDTO collectionRequestDTO) {
        CollectionEntity collectionEntity = new CollectionEntity();
        BeanUtils.copyProperties(collectionRequestDTO, collectionEntity);
        CollectionEntity savedCollection = collectionsRepository.save(collectionEntity);
        return collectionsMapper.toCollectionsDTO(savedCollection, modelMapper);
    }

    @Override
    public CollectionResponseDTO updateCollection(Long id, CollectionRequestDTO collectionRequestDTO) {
        CollectionEntity collectionEntity = collectionsRepository.findById(id).orElse(null);
        BeanUtils.copyProperties(collectionRequestDTO, collectionEntity);
        CollectionEntity updatedCollection = collectionsRepository.save(collectionEntity);
        return collectionsMapper.toCollectionsDTO(updatedCollection, modelMapper);
    }

    @Override
    public CollectionResponseDTO getCollection(Long id) {
        CollectionEntity collectionEntity = collectionsRepository.findById(id).orElse(null);
        return collectionsMapper.toCollectionsDTO(collectionEntity, modelMapper);
    }

    @Override
    public List<CollectionResponseDTO> getCollections() {
        List<CollectionEntity> collectionEntities = collectionsRepository.findAll();
        return collectionEntities.stream().map(collectionEntity -> collectionsMapper.toCollectionsDTO(collectionEntity, modelMapper)).collect(Collectors.toList());
    }

    @Override
    public void deleteCollection(Long id) {
        CollectionEntity collectionEntity = collectionsRepository.findById(id).orElse(null);
        collectionsRepository.delete(collectionEntity);
    }
}
