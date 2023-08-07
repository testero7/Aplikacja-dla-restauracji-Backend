package com.back.PracaLicencjackaBackend.service;

import com.back.PracaLicencjackaBackend.models.Review;
import com.back.PracaLicencjackaBackend.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReviewServiceImpl implements ReviewService{
    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public void addReview(Review review) {
        reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewsByProduct(Integer productId) {
        return reviewRepository.findByProductId(productId);
    }

    @Override
    public List<Review> getReviewsByUser(Integer userId) {
        return reviewRepository.findByUserId(userId);
    }
    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

}
