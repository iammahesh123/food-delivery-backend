package com.food.fooddeliverybackend.service;

import com.food.fooddeliverybackend.model.PromoCodeRequestDTO;
import com.food.fooddeliverybackend.model.PromoCodeResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PromoCodeService {
    PromoCodeResponseDTO createPromoCode(PromoCodeRequestDTO promoCodeRequestDTO);
    PromoCodeResponseDTO updatePromoCode(Long id,PromoCodeRequestDTO promoCodeRequestDTO);
    PromoCodeResponseDTO getPromoCode(Long id);
    List<PromoCodeResponseDTO> getPromoCodes();
    void deletePromoCode(Long id);
}
