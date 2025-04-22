package com.food.fooddeliverybackend.controller;

import com.food.fooddeliverybackend.enums.OrderBy;
import com.food.fooddeliverybackend.model.PageModel;
import com.food.fooddeliverybackend.model.PromoCodeRequestDTO;
import com.food.fooddeliverybackend.model.PromoCodeResponseDTO;
import com.food.fooddeliverybackend.service.PromoCodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "PromoCode Controller", description = "List of PromoCode Endpoints")
@RestController
@RequestMapping("/promo-codes")
public class PromoCodeController {

    private final PromoCodeService promoCodeService;

    public PromoCodeController(PromoCodeService promoCodeService) {
        this.promoCodeService = promoCodeService;
    }

    @Operation(summary = "Create a new promo code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Promo code created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid promo code data")
    })
    @PostMapping
    public ResponseEntity<PromoCodeResponseDTO> createPromoCode(
            @RequestBody PromoCodeRequestDTO promoCodeRequestDTO) {
        return ResponseEntity.ok().body(promoCodeService.createPromoCode(promoCodeRequestDTO));
    }

    @Operation(summary = "Update an existing promo code by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Promo code updated successfully"),
            @ApiResponse(responseCode = "404", description = "Promo code not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PromoCodeResponseDTO> updatePromoCode(
            @PathVariable Long id,
            @RequestBody PromoCodeRequestDTO promoCodeRequestDTO) {
        return ResponseEntity.ok().body(promoCodeService.updatePromoCode(id, promoCodeRequestDTO));
    }

    @Operation(summary = "Get a promo code by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Promo code retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Promo code not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PromoCodeResponseDTO> getPromoCode(@PathVariable Long id) {
        return ResponseEntity.ok().body(promoCodeService.getPromoCode(id));
    }

    @Operation(summary = "Get all promo codes with pagination and sorting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Promo codes retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<PromoCodeResponseDTO>> getAllPromoCodes(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @Parameter(description = "Sort column", example = "validUntil")
            @RequestParam(value = "sortColumn", required = false) String sortColumn,
            @Parameter(description = "Sort order", example = "DESC")
            @RequestParam(value = "orderBY", required = false) OrderBy orderBy) {

        PageModel pageModel = new PageModel(pageNumber, pageSize, sortColumn, orderBy);
        return ResponseEntity.ok().body(promoCodeService.getPromoCodes(pageModel));
    }

    @Operation(summary = "Delete a promo code by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Promo code deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Promo code not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePromoCode(@PathVariable Long id) {
        promoCodeService.deletePromoCode(id);
        return ResponseEntity.ok().body(HttpStatus.OK);
    }
}
