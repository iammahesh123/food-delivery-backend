package com.food.fooddeliverybackend.controller;

import com.food.fooddeliverybackend.enums.OrderBy;
import com.food.fooddeliverybackend.model.PageModel;
import com.food.fooddeliverybackend.model.ReviewRequestDTO;
import com.food.fooddeliverybackend.model.ReviewResponseDTO;
import com.food.fooddeliverybackend.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Create a new review")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created review"),
            @ApiResponse(responseCode = "400", description = "Invalid review request")
    })
    @PostMapping
    public ResponseEntity<ReviewResponseDTO> createReview(ReviewRequestDTO reviewRequestDTO) {
        return ResponseEntity.ok(reviewService.createReview(reviewRequestDTO));
    }

    @Operation(summary = "Update an existing review by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated review"),
            @ApiResponse(responseCode = "404", description = "Review not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponseDTO> updateReview(@PathVariable("id") Long id, ReviewRequestDTO reviewRequestDTO) {
        return ResponseEntity.ok(reviewService.updateReview(id, reviewRequestDTO));
    }

    @Operation(summary = "Get a review by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Review not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponseDTO> getReview(@PathVariable("id") Long id) {
        return ResponseEntity.ok(reviewService.getReview(id));
    }

    @Operation(summary = "Get all reviews with pagination and sorting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reviews fetched successfully")
    })
    @GetMapping
    public ResponseEntity<List<ReviewResponseDTO>> getAllReviews(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @Parameter(description = "Column to sort by", example = "createdAt")
            @RequestParam(value = "sortColumn", required = false) String sortColumn,
            @Parameter(description = "Order by ascending or descending", example = "ASC")
            @RequestParam(value = "orderBY", required = false) OrderBy orderBy)  {
        PageModel pageModel = new PageModel(pageNumber, pageSize, sortColumn, orderBy);
        return ResponseEntity.ok(reviewService.getAllReviews(pageModel));
    }

    @Operation(summary = "Delete a review by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Review not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteReview(@PathVariable("id") Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
