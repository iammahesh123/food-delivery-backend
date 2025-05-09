package com.food.fooddeliverybackend.service;

import com.food.fooddeliverybackend.model.CollectionRequestDTO;
import com.food.fooddeliverybackend.model.CollectionResponseDTO;
import com.food.fooddeliverybackend.model.PageModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CollectionService {
    CollectionResponseDTO createCollection(CollectionRequestDTO collectionRequestDTO);
    CollectionResponseDTO updateCollection(Long id, CollectionRequestDTO collectionRequestDTO);
    CollectionResponseDTO getCollection(Long id);
    List<CollectionResponseDTO> getCollections(PageModel pageModel);
    void deleteCollection(Long id);
}
