package id.ac.ui.cs.advprog.farrel.restservice;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.server.ResponseStatusException;

import id.ac.ui.cs.advprog.farrel.model.Review;
import id.ac.ui.cs.advprog.farrel.strategy.SortByRatingStrategy;
import id.ac.ui.cs.advprog.farrel.repository.ReviewDb;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class ReviewRestServiceImplTest {

    @Mock
    private ReviewDb reviewDb;

    @InjectMocks
    private ReviewRestServiceImpl reviewService;

    private Review review;
    private UUID reviewId = UUID.randomUUID();
    private SortByRatingStrategy sortByRatingStrategy = new SortByRatingStrategy();

    @BeforeEach
    void setUp() {
        review = new Review();
        review.setReviewId(reviewId);
        review.setUserId("user1");
        review.setProductId("product1");
        review.setRating(5);
        review.setReview("Great product");

        when(reviewDb.findById(reviewId)).thenReturn(Optional.of(review));
        when(reviewDb.findAll()).thenReturn(Arrays.asList(review));
        when(reviewDb.save(any(Review.class))).thenReturn(review);
    }

    @Test
    void testCreateRestReview() {
        Review savedReview = reviewService.createRestReview(review);
        assertNotNull(savedReview);
        assertEquals(reviewId, savedReview.getReviewId());
        verify(reviewDb, times(1)).save(review);
    }

    @Test
    void testRetrieveRestAllReview() {
        List<Review> reviews = reviewService.retrieveRestAllReview();
        assertFalse(reviews.isEmpty());
        assertEquals(1, reviews.size());
    }

    @Test
    void testGetRestReviewByIdFound() {
        Review foundReview = reviewService.getRestReviewById(reviewId);
        assertNotNull(foundReview);
        assertEquals(reviewId, foundReview.getReviewId());
    }

    @Test
    void testUpdateRestReview() {
        Review updatedReview = new Review();
        updatedReview.setReviewId(reviewId);
        updatedReview.setReview("Updated review text");
        updatedReview.setRating(4);

        Review result = reviewService.updateRestReview(updatedReview);
        assertNotNull(result);
        assertEquals("Updated review text", result.getReview());
    }

    @Test
    void testDeleteReview() {
        assertDoesNotThrow(() -> reviewService.restDeleteReview(reviewId));
        verify(reviewDb, times(1)).deleteById(reviewId);
    }

    @Test
    void testGetRestReviewByIdNotFound() {
        UUID randomUUID = UUID.randomUUID();
        when(reviewDb.findById(randomUUID)).thenReturn(Optional.empty());

        Review review = reviewService.getRestReviewById(randomUUID);
        assertNull(review);
    }

    @Test
    void testUpdateRestReviewNotFound() {
        Review nonExistentReview = new Review();
        nonExistentReview.setReviewId(UUID.randomUUID());

        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            reviewService.updateRestReview(nonExistentReview);
        });

        assertTrue(exception.getMessage().contains("not found"));
    }

    @Test
    void testDeleteReviewNotFound() {
        UUID randomUUID = UUID.randomUUID();
        doThrow(new EmptyResultDataAccessException(1)).when(reviewDb).deleteById(randomUUID);

        assertThrows(NoSuchElementException.class, () -> {
            reviewService.restDeleteReview(randomUUID);
        });
    }
}
