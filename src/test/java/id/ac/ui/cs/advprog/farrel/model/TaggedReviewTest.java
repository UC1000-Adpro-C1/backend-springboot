package id.ac.ui.cs.advprog.farrel.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

class TaggedReviewTest {
    private Review baseReview;
    private TaggedReview taggedReview;

    @BeforeEach
    void setUp() {
        // Setup a base Review object
        baseReview = new Review();
        baseReview.setReviewId(UUID.randomUUID());
        baseReview.setUserId("user123");
        baseReview.setProductId("product456");
        baseReview.setRating(4);
        baseReview.setReview("This is a great product.");

        // Decorate the Review object with a TaggedReview
        taggedReview = new TaggedReview(baseReview, "Featured");
    }

    @Test
    void testGetReviewId() {
        assertEquals(baseReview.getReviewId(), taggedReview.getReviewId());
    }

    @Test
    void testGetUserId() {
        assertEquals(baseReview.getUserId(), taggedReview.getUserId());
    }

    @Test
    void testGetProductId() {
        assertEquals(baseReview.getProductId(), taggedReview.getProductId());
    }

    @Test
    void testGetRating() {
        assertEquals(baseReview.getRating(), taggedReview.getRating());
    }

    @Test
    void testGetReview() {
        assertEquals(baseReview.getReview(), taggedReview.getReview());
    }

    @Test
    void testGetTag() {
        assertEquals("Featured", taggedReview.getTag());  // Note: this might be a mistake in your original class definition
    }

    @Test
    void testTagIsSetCorrectly() {
        assertEquals("Featured", taggedReview.getTag());
    }
}
