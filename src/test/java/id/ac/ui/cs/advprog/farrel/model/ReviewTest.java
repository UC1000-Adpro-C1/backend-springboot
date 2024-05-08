package id.ac.ui.cs.advprog.farrel.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReviewTest {
    private Review review;

    @BeforeEach
    void setUp() {
        review = new Review();
        review.setUserId("user12345");
        review.setProductId("prod67890");
        review.setRating(5);
        review.setReview("Great product, loved it!");
    }

    @Test
    void testUserIdNotNullAndLength() {
        assertNotNull(review.getUserId());
        assertTrue(review.getUserId().length() <= 100);
    }

    @Test
    void testProductIdNotNullAndLength() {
        assertNotNull(review.getProductId());
        assertTrue(review.getProductId().length() <= 100);
    }

    @Test
    void testRatingWithinValidRange() {
        assertTrue(review.getRating() >= 1 && review.getRating() <= 5);
    }

    @Test
    void testReviewNotNullAndLength() {
        assertNotNull(review.getReview());
        assertTrue(review.getReview().length() <= 300);
    }

    private String createStringOfLength(int length) {
        return new String(new char[length]).replace('\0', 'a');
    }
}
