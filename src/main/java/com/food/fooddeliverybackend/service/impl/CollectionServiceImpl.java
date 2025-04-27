package com.food.fooddeliverybackend.service.impl;

import com.food.fooddeliverybackend.entity.CollectionEntity;
import com.food.fooddeliverybackend.mapper.CollectionsMapper;
import com.food.fooddeliverybackend.model.CollectionRequestDTO;
import com.food.fooddeliverybackend.model.CollectionResponseDTO;
import com.food.fooddeliverybackend.model.PageModel;
import com.food.fooddeliverybackend.repository.CollectionsRepository;
import com.food.fooddeliverybackend.service.CollectionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.food.fooddeliverybackend.util.PaginationUtility.applyPagination;

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
        if (collectionEntity.getRestaurantEntities() != null) {
            collectionEntity.setPlaces(collectionEntity.getRestaurantEntities().size());
        } else {
            collectionEntity.setPlaces(0);
        }
        CollectionEntity savedCollection = collectionsRepository.save(collectionEntity);
        return collectionsMapper.toCollectionsDTO(savedCollection, modelMapper);
    }

    @Override
    public CollectionResponseDTO updateCollection(Long id, CollectionRequestDTO collectionRequestDTO) {
        CollectionEntity collectionEntity = collectionsRepository.findById(id).orElse(null);
        BeanUtils.copyProperties(collectionRequestDTO, collectionEntity);
        if (collectionEntity.getRestaurantEntities() != null) {
            collectionEntity.setPlaces(collectionEntity.getRestaurantEntities().size());
        } else {
            collectionEntity.setPlaces(0);
        }
        CollectionEntity updatedCollection = collectionsRepository.save(collectionEntity);
        return collectionsMapper.toCollectionsDTO(updatedCollection, modelMapper);
    }

    @Override
    public CollectionResponseDTO getCollection(Long id) {
        CollectionEntity collectionEntity = collectionsRepository.findById(id).orElse(null);
        return collectionsMapper.toCollectionsDTO(collectionEntity, modelMapper);
    }

    @Override
    public List<CollectionResponseDTO> getCollections(PageModel pageModel) {
        Page<CollectionEntity> collectionEntities = collectionsRepository.findAll(applyPagination(pageModel));
        return collectionEntities.stream().map(collectionEntity -> collectionsMapper.toCollectionsDTO(collectionEntity, modelMapper)).collect(Collectors.toList());
    }

    @Override
    public void deleteCollection(Long id) {
        CollectionEntity collectionEntity = collectionsRepository.findById(id).orElse(null);
        collectionsRepository.delete(collectionEntity);
    }
}
