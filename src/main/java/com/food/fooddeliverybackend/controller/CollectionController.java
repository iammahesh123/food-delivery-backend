package com.food.fooddeliverybackend.controller;

import com.food.fooddeliverybackend.model.CollectionRequestDTO;
import com.food.fooddeliverybackend.model.CollectionResponseDTO;
import com.food.fooddeliverybackend.service.CollectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collections")
public class CollectionController {

    private final CollectionService collectionService;

    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @PostMapping
    public ResponseEntity<CollectionResponseDTO> createCollection(CollectionRequestDTO collectionRequestDTO) {
        return ResponseEntity.ok(collectionService.createCollection(collectionRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CollectionResponseDTO> updateCollection(@PathVariable Long id, CollectionRequestDTO collectionRequestDTO) {
        return ResponseEntity.ok(collectionService.updateCollection(id, collectionRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CollectionResponseDTO> getCollection(@PathVariable Long id) {
        return ResponseEntity.ok(collectionService.getCollection(id));
    }

    @GetMapping
    public ResponseEntity<List<CollectionResponseDTO>> getCollections() {
        return ResponseEntity.ok(collectionService.getCollections());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCollection(@PathVariable Long id) {
        collectionService.deleteCollection(id);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }
}
