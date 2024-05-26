package id.ac.ui.cs.advprog.farrel.restservice;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import id.ac.ui.cs.advprog.farrel.model.Review;

import java.util.HashMap;
import java.util.Map;

@Service
public interface ReviewRestService {
    Review createRestReview(Review buku);
    List<Review> retrieveRestAllReview();
    Review getRestReviewById(UUID id);
    Review updateRestReview(Review bukuFromDTO);
    List<Review> getRestReviewByProductId(String id);
    void restDeleteReview(UUID id);
    List<Review> getAllReviewsSortedByRating(String id);
    List<Review> getAllReviewsSortedByRatingDesc(String id);
}
