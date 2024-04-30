package id.ac.ui.cs.advprog.farrel.restservice;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import id.ac.ui.cs.advprog.farrel.model.Review;
import id.ac.ui.cs.advprog.farrel.repository.ReviewDb;

@ExtendWith(MockitoExtension.class)
public class ReviewRestServiceImplTest {

    @Mock
    private ReviewDb reviewDb;

    @InjectMocks
    private ReviewRestServiceImpl reviewService;

    private Review review1, review2;

    @BeforeEach
    public void setUp() {
        review1 = new Review();
        review1.setReviewId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        review1.setUserId("eb558e9f-1c39-460e-8860-12345678");
        review1.setProductId("eb558e9f-1c39-460e-8860-1111111");
        review1.setRating(5);
        review1.setReview("Excellent");

        review2 = new Review();
        review2.setReviewId("eb558e9f-1c39-460e-8860-71af6af63bd7");
        review2.setUserId("eb558e9f-1c39-460e-8860-12345678");
        review2.setProductId("eb558e9f-1c39-460e-8860-1111112");
        review2.setRating(2);
        review2.setReview("A staple for any wardrobe.");
    }

    @Test
    public void testCreateRestReview() {
        when(reviewDb.save(review1)).thenReturn(review1);
        Review savedReview = reviewService.createRestReview(review1);
        assertNotNull(savedReview);
        assertEquals("Excellent", savedReview.getReview());
        verify(reviewDb, times(1)).save(review1);
    }

    @Test
    public void testRetrieveRestAllReview() {
        when(reviewDb.findAll()).thenReturn(Arrays.asList(review1, review2));
        List<Review> reviews = reviewService.retrieveRestAllReview();
        assertEquals(2, reviews.size());
        verify(reviewDb, times(1)).findAll();
    }

    @Test
    public void testGetRestReviewByIdFound() {
        when(reviewDb.findAll()).thenReturn(Arrays.asList(review1, review2));
        Review foundReview = reviewService.getRestReviewById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertNotNull(foundReview);
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", foundReview.getReviewId());
    }

    @Test
    public void testGetRestReviewByIdNotFound() {
        when(reviewDb.findAll()).thenReturn(Arrays.asList(review1));
        Review foundReview = reviewService.getRestReviewById("2");
        assertNull(foundReview);
    }

    @Test
    public void testUpdateRestReviewFound() {
        when(reviewDb.findAll()).thenReturn(Arrays.asList(review1));
        when(reviewDb.save(any(Review.class))).thenReturn(review1);
        review1.setReview("Updated Review");
        review1.setRating(3);
        Review updatedReview = reviewService.updateRestReview(review1);
        assertNotNull(updatedReview);
        assertEquals("Updated Review", updatedReview.getReview());
        assertEquals(3, updatedReview.getRating());
    }

    @Test
    public void testUpdateRestReviewNotFound() {
        when(reviewDb.findAll()).thenReturn(Arrays.asList(review1));
        assertThrows(ResponseStatusException.class, () -> {
            reviewService.updateRestReview(review2);
        });
    }


    @Test
    public void testGetRestReviewByProductIdSuccess() {
        when(reviewDb.findByProductId("eb558e9f-1c39-460e-8860-1111111")).thenReturn(Arrays.asList(review1));

        List<Review> reviews = reviewService.getRestReviewByProductId("eb558e9f-1c39-460e-8860-1111111");
        assertNotNull(reviews);
        assertEquals(1, reviews.size());
        assertEquals("Excellent", reviews.get(0).getReview());
        verify(reviewDb, times(1)).findByProductId("eb558e9f-1c39-460e-8860-1111111");
    }

    @Test
    public void testGetRestReviewByProductIdEmpty() {
        when(reviewDb.findByProductId("non-existing-id")).thenReturn(Collections.emptyList());

        List<Review> reviews = reviewService.getRestReviewByProductId("non-existing-id");
        assertNotNull(reviews);
        assertTrue(reviews.isEmpty());
        verify(reviewDb, times(1)).findByProductId("non-existing-id");
    }
    @Test
    public void testRestDeleteReviewSuccess() {
        doNothing().when(reviewDb).deleteById("eb558e9f-1c39-460e-8860-71af6af63bd6");

        assertDoesNotThrow(() -> reviewService.restDeleteReview("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        verify(reviewDb, times(1)).deleteById("eb558e9f-1c39-460e-8860-71af6af63bd6");
    }
    @Test
    public void testRestDeleteReviewNotFound() {
        doThrow(new EmptyResultDataAccessException(1)).when(reviewDb).deleteById("non-existing-id");

        Exception exception = assertThrows(NoSuchElementException.class, () -> reviewService.restDeleteReview("non-existing-id"));
        assertEquals("No review found with ID: non-existing-id", exception.getMessage());
        verify(reviewDb, times(1)).deleteById("non-existing-id");
    }




}
