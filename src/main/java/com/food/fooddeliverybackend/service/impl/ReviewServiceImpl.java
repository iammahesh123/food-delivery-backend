package com.food.fooddeliverybackend.service.impl;

import com.food.fooddeliverybackend.entity.ReviewEntity;
import com.food.fooddeliverybackend.mapper.ReviewMapper;
import com.food.fooddeliverybackend.model.PageModel;
import com.food.fooddeliverybackend.model.ReviewRequestDTO;
import com.food.fooddeliverybackend.model.ReviewResponseDTO;
import com.food.fooddeliverybackend.repository.FoodItemRepository;
import com.food.fooddeliverybackend.repository.RestaurantRepository;
import com.food.fooddeliverybackend.repository.ReviewRepository;
import com.food.fooddeliverybackend.repository.UserRepository;
import com.food.fooddeliverybackend.service.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.food.fooddeliverybackend.util.PaginationUtility.applyPagination;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final ModelMapper modelMapper;
    private final FoodItemRepository foodItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewMapper reviewMapper, ModelMapper modelMapper, FoodItemRepository foodItemRepository, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
        this.modelMapper = modelMapper;
        this.foodItemRepository = foodItemRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ReviewResponseDTO createReview(ReviewRequestDTO reviewRequestDTO) {
        ReviewEntity reviewEntity = new ReviewEntity();
        BeanUtils.copyProperties(reviewRequestDTO, reviewEntity);
        if (reviewRequestDTO.getFoodItemId() > 0) {
            foodItemRepository.findById(reviewRequestDTO.getFoodItemId()).ifPresent(reviewEntity::setFoodItemEntity);
        }
        if (reviewRequestDTO.getRestaurantId() > 0) {
            restaurantRepository.findById(reviewRequestDTO.getRestaurantId()).ifPresent(reviewEntity::setRestaurantEntity);
        }
        if(reviewRequestDTO.getUserId() > 0) {
            userRepository.findById(reviewRequestDTO.getUserId()).ifPresent(reviewEntity::setUserEntity);
        }

        ReviewEntity savedReview = reviewRepository.save(reviewEntity);
        return reviewMapper.toDTO(savedReview,modelMapper);
    }

    @Override
    public ReviewResponseDTO updateReview(Long id, ReviewRequestDTO reviewRequestDTO) {
        ReviewEntity reviewEntity = reviewRepository.findById(id).orElse(null);
        BeanUtils.copyProperties(reviewRequestDTO, reviewEntity);
        if (reviewRequestDTO.getFoodItemId() > 0) {
            foodItemRepository.findById(reviewRequestDTO.getFoodItemId())
                    .ifPresent(reviewEntity::setFoodItemEntity);
        } else {
            reviewEntity.setFoodItemEntity(null);
        }
        if (reviewRequestDTO.getRestaurantId() > 0) {
            restaurantRepository.findById(reviewRequestDTO.getRestaurantId())
                    .ifPresent(reviewEntity::setRestaurantEntity);
        } else {
            reviewEntity.setRestaurantEntity(null);
        }
        if(reviewRequestDTO.getUserId() > 0) {
            userRepository.findById(reviewRequestDTO.getUserId()).ifPresent(reviewEntity::setUserEntity);
        }
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
        Page<ReviewEntity> reviews = reviewRepository.findAll(applyPagination(pageModel));
        return reviews.stream().map(reviewEntity -> reviewMapper.toDTO(reviewEntity,modelMapper)).collect(Collectors.toList());
    }

    @Override
    public void deleteReview(Long id) {
        ReviewEntity reviewEntity = reviewRepository.findById(id).orElse(null);
        reviewRepository.delete(reviewEntity);
    }
}
