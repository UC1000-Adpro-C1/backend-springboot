package id.ac.ui.cs.advprog.farrel.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class ReviewTest{
    Review review;
    
    @BeforeEach
    void setUp(){
        this.review = new Review();
        this.review.setReviewId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        this.review.setUserId("eb558e9f-1c39-460e-8860-12345678");
        this.review.setProductId("eb558e9f-1c39-460e-8860-1111111");
        this.review.setRating(5);
        this.review.setReview("Perfect for layering or as a standalone piece. A staple for any wardrobe.");
    }
    @Test
    void testGetReviewId(){
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", this.review.getReviewId());
    }

    @Test
    void testGetUserId(){
        assertEquals("eb558e9f-1c39-460e-8860-12345678", this.review.getUserId());
    }
 
    @Test
    void testGetProductId(){
        assertEquals("eb558e9f-1c39-460e-8860-1111111", this.review.getProductId());
    }
    @Test
    void testGetRate(){
        assertEquals(5, this.review.getRating());
    }
    @Test
    void testGetReview(){
        assertEquals("Perfect for layering or as a standalone piece. A staple for any wardrobe.", this.review.getReview());
    }
}