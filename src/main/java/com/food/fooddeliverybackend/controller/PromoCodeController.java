package com.food.fooddeliverybackend.controller;

import com.food.fooddeliverybackend.model.PromoCodeRequestDTO;
import com.food.fooddeliverybackend.model.PromoCodeResponseDTO;
import com.food.fooddeliverybackend.service.PromoCodeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<PromoCodeResponseDTO>> getAllPromoCodes() {
        return ResponseEntity.ok().body(promoCodeService.getPromoCodes());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePromoCode(@PathVariable Long id) {
        promoCodeService.deletePromoCode(id);
        return ResponseEntity.ok().body(HttpStatus.OK);
    }
}
