package com.back.PracaLicencjackaBackend.controller;

import com.back.PracaLicencjackaBackend.models.Review;
import com.back.PracaLicencjackaBackend.models.ReviewDTO;
import com.back.PracaLicencjackaBackend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/create")
    public ResponseEntity<String> addReview(@RequestBody ReviewDTO reviewDto) {
        Review review = new Review();
        review.setUserId(reviewDto.getUserId());
        review.setProductId(reviewDto.getProductId());
        review.setDescription(reviewDto.getDescription());
        reviewService.addReview(review);
        return ResponseEntity.ok("Udało się dodać recenzję.");
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getReviewsByProduct(@PathVariable Integer productId) {
        List<Review> reviews = reviewService.getReviewsByProduct(productId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Review>> getReviewsByUser(@PathVariable Integer userId) {
        List<Review> reviews = reviewService.getReviewsByUser(userId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/showAllReviews")
    public ResponseEntity<List<Review>> getAllReviews() {
        List<Review> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }
}
