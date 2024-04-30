package id.ac.ui.cs.advprog.farrel.restcontroller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import id.ac.ui.cs.advprog.farrel.model.Review;
import id.ac.ui.cs.advprog.farrel.restservice.ReviewRestService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.HashMap;

@RestController 
@RequestMapping("/api")
public class ReviewRestController {
    @Autowired
    private ReviewRestService reviewRestService;

    @CrossOrigin("*")
    @GetMapping("/review/view-all")
    private List<Review> retrieveAllReview() {
        return reviewRestService.retrieveRestAllReview();
    }

    @GetMapping("/review/{id}")
    private ResponseEntity<Review> retrieveReview(@PathVariable("id") String id) {
        try {
            var review = reviewRestService.getRestReviewById(id);
            return new ResponseEntity<>(review, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id Review " + id + " not found");
        }
    }
    @PostMapping("/review/create")
    public ResponseEntity<Review> restAddReview(@RequestBody Review review, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field");
        } else {
            reviewRestService.createRestReview(review);
            return new ResponseEntity<>(review, HttpStatus.CREATED);
        }
    }
    // update review rest
    @PutMapping("/review")
    public ResponseEntity<Review> restUpdateReview(@RequestBody Review review, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field");
        } else {
            var res = reviewRestService.updateRestReview(review);
           return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }
    
    @DeleteMapping("/delReview/{id}")
    public ResponseEntity<String> restDeleteReview(@PathVariable("id") String id) {
        try {
            reviewRestService.restDeleteReview(id);
            return ResponseEntity.ok("Review berhasil dihapus!");
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review tidak ditemukan");
        }
    }

    @GetMapping("/reviewProduct/{id}")
    private List<Review> findReviewByProductId(@PathVariable("id") String id) {
        try {
            return reviewRestService.getRestReviewByProductId(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id Review " + id + " not found");
        }
    }
  

}