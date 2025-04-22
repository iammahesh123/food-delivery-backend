package com.food.fooddeliverybackend.controller;

import com.food.fooddeliverybackend.enums.OrderBy;
import com.food.fooddeliverybackend.model.AddressRequestDTO;
import com.food.fooddeliverybackend.model.AddressResponseDTO;
import com.food.fooddeliverybackend.model.PageModel;
import com.food.fooddeliverybackend.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @Operation(summary = "Create a new Address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    @PostMapping
    public ResponseEntity<AddressResponseDTO> createAddress(@RequestBody AddressRequestDTO addressRequestDTO) {
        return ResponseEntity.ok(addressService.createAddress(addressRequestDTO));
    }

    @Operation(summary = "Update the Address by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address updated successfully"),
            @ApiResponse(responseCode = "404", description = "Address not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> updateAddress(@PathVariable("id") Long id, @RequestBody AddressRequestDTO addressRequestDTO) {
        return ResponseEntity.ok(addressService.updateAddress(id, addressRequestDTO));
    }

    @Operation(summary = "Get a Address by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Address not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> getAddress(@PathVariable("id") Long id) {
        return ResponseEntity.ok(addressService.getAddressById(id));
    }

    @Operation(summary = "Get all Address with pagination and sorting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All Address retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<AddressResponseDTO>> getAddresses(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @Parameter(description = "Field to sort by", example = "createdAt")
            @RequestParam(value = "sortColumn", required = false) String sortColumn,
            @Parameter(description = "Sort direction", example = "ASC")
            @RequestParam(value = "orderBY", required = false) OrderBy orderBy) {

        PageModel pageModel = new PageModel(pageNumber, pageSize, sortColumn, orderBy);
        return ResponseEntity.ok(addressService.getAllAddresses(pageModel));
    }

    @Operation(summary = "Delete a address by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Address not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAddress(@PathVariable("id") Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
