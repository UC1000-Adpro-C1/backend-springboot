package id.ac.ui.cs.advprog.farrel.restservice;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import id.ac.ui.cs.advprog.farrel.model.Review;
import id.ac.ui.cs.advprog.farrel.repository.ReviewDb;
import id.ac.ui.cs.advprog.farrel.strategy.SortByRatingStrategy;
import id.ac.ui.cs.advprog.farrel.strategy.SortByRatingStrategyDesc;

import java.util.List;
import java.util.NoSuchElementException;


@Service
public class ReviewRestServiceImpl implements ReviewRestService{
    @Autowired
    private ReviewDb reviewDb;
    private SortByRatingStrategy sortByRating;

    @Override
    public Review createRestReview(Review review){
        reviewDb.save(review);
        return review;
    }
    @Override
    public List<Review> retrieveRestAllReview() {
        return reviewDb.findAll();
    }

    @Override
    public Review getRestReviewById(UUID id){
        for (Review review : retrieveRestAllReview()) {
            if (review.getReviewId().equals(id)) {
                return review;
            }
        }
        return null;
    }
    @Override
    public Review updateRestReview(Review updateReview){
        Review review = getRestReviewById(updateReview.getReviewId());
        if (review != null) {
            review.setReview(updateReview.getReview());
            review.setRating(updateReview.getRating());
            reviewDb.save(review);
            return review;
        } else {
             throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review with ID " + updateReview.getReviewId() + " not found");
        }
    }
    @Override
    public List<Review> getRestReviewByProductId(String id){
        return reviewDb.findByProductId(id);
    }
   
    @Override 
    public void restDeleteReview(UUID id) {
        try {
            reviewDb.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchElementException("No review found with ID: " + id);
        }
    }

    public List<Review> getAllReviewsSortedByRating(String id) {
        List<Review> reviews = reviewDb.findByProductId(id);
        sortByRating = new SortByRatingStrategy();
        return sortByRating.sort(reviews);
    }
    public List<Review> getAllReviewsSortedByRatingDesc(String id) {
        List<Review> reviews = reviewDb.findByProductId(id);
        SortByRatingStrategyDesc sortByRating = new SortByRatingStrategyDesc();
        return sortByRating.sort(reviews);
    }
    
}
