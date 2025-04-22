package com.food.fooddeliverybackend.controller;

import com.food.fooddeliverybackend.enums.OrderBy;
import com.food.fooddeliverybackend.model.CollectionRequestDTO;
import com.food.fooddeliverybackend.model.CollectionResponseDTO;
import com.food.fooddeliverybackend.model.PageModel;
import com.food.fooddeliverybackend.service.CollectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Collections Controller", description = "List of Collections Endpoints")
@RestController
@RequestMapping("/collections")
public class CollectionController {

    private final CollectionService collectionService;

    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @Operation(summary = "Create a new collection")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Collection created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid collection data")
    })
    @PostMapping
    public ResponseEntity<CollectionResponseDTO> createCollection(
            @RequestBody CollectionRequestDTO collectionRequestDTO) {
        return ResponseEntity.ok(collectionService.createCollection(collectionRequestDTO));
    }

    @Operation(summary = "Update an existing collection by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Collection updated successfully"),
            @ApiResponse(responseCode = "404", description = "Collection not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CollectionResponseDTO> updateCollection(
            @PathVariable Long id,
            @RequestBody CollectionRequestDTO collectionRequestDTO) {
        return ResponseEntity.ok(collectionService.updateCollection(id, collectionRequestDTO));
    }

    @Operation(summary = "Get a collection by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Collection retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Collection not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CollectionResponseDTO> getCollection(
            @PathVariable Long id) {
        return ResponseEntity.ok(collectionService.getCollection(id));
    }

    @Operation(summary = "Get all collections with pagination and sorting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Collections retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<CollectionResponseDTO>> getCollections(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @Parameter(description = "Sort by column", example = "name")
            @RequestParam(value = "sortColumn", required = false) String sortColumn,
            @Parameter(description = "Order by ASC or DESC", example = "ASC")
            @RequestParam(value = "orderBY", required = false) OrderBy orderBy) {

        PageModel pageModel = new PageModel(pageNumber, pageSize, sortColumn, orderBy);
        return ResponseEntity.ok(collectionService.getCollections(pageModel));
    }

    @Operation(summary = "Delete a collection by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Collection deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Collection not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCollection(
            @PathVariable Long id) {
        collectionService.deleteCollection(id);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }
}
