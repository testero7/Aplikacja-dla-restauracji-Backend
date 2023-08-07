package com.back.PracaLicencjackaBackend.service;

import com.back.PracaLicencjackaBackend.models.Review;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ReviewService {

    public void addReview(Review review);
    public List<Review> getReviewsByProduct(Integer productId);

    public List<Review> getReviewsByUser(Integer userId);

    public List<Review> getAllReviews();

}
