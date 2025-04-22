package com.food.fooddeliverybackend.controller;

import com.food.fooddeliverybackend.enums.OrderBy;
import com.food.fooddeliverybackend.model.PageModel;
import com.food.fooddeliverybackend.model.PromoCodeRequestDTO;
import com.food.fooddeliverybackend.model.PromoCodeResponseDTO;
import com.food.fooddeliverybackend.service.PromoCodeService;
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

    @PostMapping
    public ResponseEntity<PromoCodeResponseDTO> createPromoCode(@RequestBody PromoCodeRequestDTO promoCodeRequestDTO) {
        return ResponseEntity.ok().body(promoCodeService.createPromoCode(promoCodeRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PromoCodeResponseDTO> updatePromoCode(@PathVariable Long id, @RequestBody PromoCodeRequestDTO promoCodeRequestDTO) {
        return ResponseEntity.ok().body(promoCodeService.updatePromoCode(id, promoCodeRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PromoCodeResponseDTO> getPromoCode(@PathVariable Long id) {
        return ResponseEntity.ok().body(promoCodeService.getPromoCode(id));
    }

    @GetMapping
    public ResponseEntity<List<PromoCodeResponseDTO>> getAllPromoCodes(
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "sortColumn", required = false) String sortColumn,
            @RequestParam(value = "orderBY", required = false) OrderBy orderBy) {
        PageModel pageModel = new PageModel(pageNumber, pageSize, sortColumn, orderBy);
        return ResponseEntity.ok().body(promoCodeService.getPromoCodes(pageModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePromoCode(@PathVariable Long id) {
        promoCodeService.deletePromoCode(id);
        return ResponseEntity.ok().body(HttpStatus.OK);
    }
}
