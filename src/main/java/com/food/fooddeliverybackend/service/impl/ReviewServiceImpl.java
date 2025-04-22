package com.food.fooddeliverybackend.service.impl;

import com.food.fooddeliverybackend.entity.ReviewEntity;
import com.food.fooddeliverybackend.mapper.ReviewMapper;
import com.food.fooddeliverybackend.model.PageModel;
import com.food.fooddeliverybackend.model.ReviewRequestDTO;
import com.food.fooddeliverybackend.model.ReviewResponseDTO;
import com.food.fooddeliverybackend.repository.ReviewRepository;
import com.food.fooddeliverybackend.service.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final ModelMapper modelMapper;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewMapper reviewMapper, ModelMapper modelMapper) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public ReviewResponseDTO createReview(ReviewRequestDTO reviewRequestDTO) {
        ReviewEntity reviewEntity = new ReviewEntity();
        BeanUtils.copyProperties(reviewRequestDTO, reviewEntity);
        ReviewEntity savedReview = reviewRepository.save(reviewEntity);
        return reviewMapper.toDTO(savedReview,modelMapper);
    }

    @Override
    public ReviewResponseDTO updateReview(Long id, ReviewRequestDTO reviewRequestDTO) {
        ReviewEntity reviewEntity = reviewRepository.findById(id).orElse(null);
        BeanUtils.copyProperties(reviewRequestDTO, reviewEntity);
        ReviewEntity updatedReview = reviewRepository.save(reviewEntity);
        return reviewMapper.toDTO(updatedReview,modelMapper);
    }

    @Override
    public ReviewResponseDTO getReview(Long id) {
        ReviewEntity reviewEntity = reviewRepository.findById(id).orElse(null);
        return reviewMapper.toDTO(reviewEntity,modelMapper);
    }

    @Override
    public List<ReviewResponseDTO> getAllReviews(PageModel pageModel) {
        List<ReviewEntity> reviews = reviewRepository.findAll();
        return reviews.stream().map(reviewEntity -> reviewMapper.toDTO(reviewEntity,modelMapper)).collect(Collectors.toList());
    }

    @Override
    public void deleteReview(Long id) {
        ReviewEntity reviewEntity = reviewRepository.findById(id).orElse(null);
        reviewRepository.delete(reviewEntity);
    }
}
