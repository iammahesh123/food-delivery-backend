package com.food.fooddeliverybackend.service;

import com.food.fooddeliverybackend.model.PageModel;
import com.food.fooddeliverybackend.model.ReviewRequestDTO;
import com.food.fooddeliverybackend.model.ReviewResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {
    ReviewResponseDTO createReview(ReviewRequestDTO reviewRequestDTO);
    ReviewResponseDTO updateReview(Long id,ReviewRequestDTO reviewRequestDTO);
    ReviewResponseDTO getReview(Long id);
    List<ReviewResponseDTO> getAllReviews(PageModel pageModel);
    void deleteReview(Long id);
}
