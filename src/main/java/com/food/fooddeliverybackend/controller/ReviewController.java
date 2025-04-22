package com.food.fooddeliverybackend.controller;

import com.food.fooddeliverybackend.enums.OrderBy;
import com.food.fooddeliverybackend.model.PageModel;
import com.food.fooddeliverybackend.model.ReviewRequestDTO;
import com.food.fooddeliverybackend.model.ReviewResponseDTO;
import com.food.fooddeliverybackend.service.ReviewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Review Controller", description = "List of Review Endpoints")
@RestController
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<ReviewResponseDTO> createReview(ReviewRequestDTO reviewRequestDTO) {
        return ResponseEntity.ok(reviewService.createReview(reviewRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponseDTO> updateReview(@PathVariable("id") Long id, ReviewRequestDTO reviewRequestDTO) {
        return ResponseEntity.ok(reviewService.updateReview(id, reviewRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponseDTO> getReview(@PathVariable("id") Long id) {
        return ResponseEntity.ok(reviewService.getReview(id));
    }

    @GetMapping
    public ResponseEntity<List<ReviewResponseDTO>> getAllReviews(
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "sortColumn", required = false) String sortColumn,
            @RequestParam(value = "orderBY", required = false) OrderBy orderBy) {
        PageModel pageModel = new PageModel(pageNumber, pageSize, sortColumn, orderBy);
        return ResponseEntity.ok(reviewService.getAllReviews(pageModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteReview(@PathVariable("id") Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
