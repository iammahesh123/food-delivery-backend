package com.food.fooddeliverybackend.service.impl;

import com.food.fooddeliverybackend.entity.PromoCodeEntity;
import com.food.fooddeliverybackend.mapper.PromoCodeMapper;
import com.food.fooddeliverybackend.model.PageModel;
import com.food.fooddeliverybackend.model.PromoCodeRequestDTO;
import com.food.fooddeliverybackend.model.PromoCodeResponseDTO;
import com.food.fooddeliverybackend.repository.PromoCodeRepository;
import com.food.fooddeliverybackend.service.PromoCodeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.food.fooddeliverybackend.util.PaginationUtility.applyPagination;

@Service
public class PromoCodeServiceImpl implements PromoCodeService {
    private final PromoCodeRepository promoCodeRepository;
    private final PromoCodeMapper promoCodeMapper;
    private final ModelMapper modelMapper;

    public PromoCodeServiceImpl(PromoCodeRepository promoCodeRepository, PromoCodeMapper promoCodeMapper, ModelMapper modelMapper) {
        this.promoCodeRepository = promoCodeRepository;
        this.promoCodeMapper = promoCodeMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public PromoCodeResponseDTO createPromoCode(PromoCodeRequestDTO promoCodeRequestDTO) {
        PromoCodeEntity promoCodeEntity = new PromoCodeEntity();
        BeanUtils.copyProperties(promoCodeRequestDTO, promoCodeEntity);
        PromoCodeEntity savedCode = promoCodeRepository.save(promoCodeEntity);
        return promoCodeMapper.toDTO(savedCode,modelMapper);
    }

    @Override
    public PromoCodeResponseDTO updatePromoCode(Long id, PromoCodeRequestDTO promoCodeRequestDTO) {
        PromoCodeEntity existingCode = promoCodeRepository.findById(id).orElse(null);
        BeanUtils.copyProperties(promoCodeRequestDTO, existingCode);
        PromoCodeEntity updatedCode = promoCodeRepository.save(existingCode);
        return promoCodeMapper.toDTO(updatedCode,modelMapper);
    }

    @Override
    public PromoCodeResponseDTO getPromoCode(Long id) {
        PromoCodeEntity existingCode = promoCodeRepository.findById(id).orElse(null);
        return promoCodeMapper.toDTO(existingCode,modelMapper);
    }

    @Override
    public List<PromoCodeResponseDTO> getPromoCodes(PageModel pageModel) {
        Page<PromoCodeEntity> promoCodeEntities = promoCodeRepository.findAll(applyPagination(pageModel));
        return promoCodeEntities.stream().map(codeEntity -> promoCodeMapper.toDTO(codeEntity,modelMapper)).collect(Collectors.toList());
    }

    @Override
    public void deletePromoCode(Long id) {
        PromoCodeEntity existingCode = promoCodeRepository.findById(id).orElse(null);
        promoCodeRepository.delete(existingCode);
    }
}
