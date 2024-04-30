package id.ac.ui.cs.advprog.farrel.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import id.ac.ui.cs.advprog.farrel.model.Review;
import id.ac.ui.cs.advprog.farrel.repository.ReviewDb;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.NoSuchElementException;


@Service
public class ReviewRestServiceImpl implements ReviewRestService{
    @Autowired
    private ReviewDb reviewDb;
    
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
    public Review getRestReviewById(String id){
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
    public void restDeleteReview(String id) {
        try {
            reviewDb.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchElementException("No review found with ID: " + id);
        }
    }



}
