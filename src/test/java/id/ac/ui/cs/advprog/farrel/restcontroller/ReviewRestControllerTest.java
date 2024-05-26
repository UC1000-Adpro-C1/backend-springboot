package id.ac.ui.cs.advprog.farrel.restcontroller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import id.ac.ui.cs.advprog.farrel.model.Review;
import id.ac.ui.cs.advprog.farrel.restservice.ReviewRestService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ReviewRestController.class)
public class ReviewRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewRestService reviewRestService;

    private Review review;
    private UUID reviewId;

    @BeforeEach
    void setUp() {
        reviewId = UUID.randomUUID();
        review = new Review(reviewId, "user123", "product123", 5, "Excellent!");
    }

    @Test
    void testRetrieveAllReviews() throws Exception {
        when(reviewRestService.retrieveRestAllReview()).thenReturn(Arrays.asList(review));

        mockMvc.perform(get("/api/review/view-all"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].userId").value("user123"));
    }
    @Test
    void testRetrieveAllReviewsEmpty() throws Exception {
        when(reviewRestService.retrieveRestAllReview()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/review/view-all"))
            .andExpect(status().isOk())
            .andExpect(content().string("[]"));
    }
    @Test
    void testRetrieveReview() throws Exception {
        when(reviewRestService.getRestReviewById(reviewId)).thenReturn(review);

        mockMvc.perform(get("/api/review/{id}", reviewId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.userId").value("user123"));
    }
    @Test
    void testRetrieveReviewNotFound() throws Exception {
        when(reviewRestService.getRestReviewById(reviewId)).thenThrow(new NoSuchElementException());

        mockMvc.perform(get("/api/review/{id}", reviewId))
            .andExpect(status().isNotFound());
    }
    @Test
    void whenPostValidReview_thenCreateReview() throws Exception {
        Review review = new Review();
        when(reviewRestService.createRestReview(any(Review.class))).thenReturn(review);

        mockMvc.perform(post("/api/review/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(review)))
            .andExpect(status().isCreated());
    }
    @Test
    void testDeleteReview() throws Exception {
        doNothing().when(reviewRestService).restDeleteReview(reviewId);

        mockMvc.perform(delete("/api/delReview/{id}", reviewId))
            .andExpect(status().isOk())
            .andExpect(content().string("Review berhasil dihapus!"));
    }

    @Test
    void testDeleteReviewNotFound() throws Exception {
        doThrow(new NoSuchElementException()).when(reviewRestService).restDeleteReview(reviewId);

        mockMvc.perform(delete("/api/delReview/{id}", reviewId))
            .andExpect(status().isNotFound());
    }

    @Test
    void testFindReviewByProductId() throws Exception {
        when(reviewRestService.getRestReviewByProductId("product123")).thenReturn(Arrays.asList(review));

        mockMvc.perform(get("/api/reviewProduct/{id}", "product123"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].productId").value("product123"));
    }
    @Test
    void testFindReviewByProductIdNotFound() throws Exception {
        when(reviewRestService.getRestReviewByProductId("product123")).thenThrow(new NoSuchElementException());

        mockMvc.perform(get("/api/reviewProduct/{id}", "product123"))
            .andExpect(status().isNotFound());
    }
    @Test
    public void testGetReviewsSortedByRatingSuccess() throws Exception {
        String productId = "product123";
        UUID reviewId = UUID.randomUUID();
        UUID reviewId2 = UUID.randomUUID();
        Review review1 = new Review(reviewId, "user123", "product123", 1, "EW!");
        Review review2 = new Review(reviewId2, "user123", "product123", 5, "Excellent!");
        List<Review> reviews = Arrays.asList(review1, review2);

        Mockito.when(reviewRestService.getAllReviewsSortedByRating(productId)).thenReturn(reviews);

        mockMvc.perform(get("/api/reviewProduct/sortedByRating/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].reviewId").value(reviewId.toString()))
                .andExpect(jsonPath("$[0].userId").value("user123"))
                .andExpect(jsonPath("$[0].productId").value("product123"))
                .andExpect(jsonPath("$[0].review").value("EW!"))
                .andExpect(jsonPath("$[0].rating").value(1))
                .andExpect(jsonPath("$[1].reviewId").value(reviewId2.toString()))
                .andExpect(jsonPath("$[1].userId").value("user123"))
                .andExpect(jsonPath("$[1].productId").value("product123"))
                .andExpect(jsonPath("$[1].review").value("Excellent!"))
                .andExpect(jsonPath("$[1].rating").value(5));
    }

    @Test
    public void testGetReviewsSortedByRatingNotFound() throws Exception {
        String productId = "123";

        Mockito.when(reviewRestService.getAllReviewsSortedByRating(productId)).thenThrow(new NoSuchElementException());

        mockMvc.perform(get("/reviewProduct/sortedByRating/{id}", productId))
                .andExpect(status().isNotFound());
    }
    @Test
    void testRestUpdateReview_ValidReview() throws Exception {
        // Arrange
        UUID reviewId = UUID.randomUUID();
        Review review = new Review(reviewId, "user123", "product123", 5, "Excellent!");
        when(reviewRestService.updateRestReview(any(Review.class))).thenReturn(review);

        // Act and Assert
        mockMvc.perform(put("/api/review")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(review)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.userId").value("user123"))
            .andExpect(jsonPath("$.productId").value("product123"))
            .andExpect(jsonPath("$.rating").value(5))
            .andExpect(jsonPath("$.review").value("Excellent!"));
    }
    @Test
    void testRestUpdateReview_InValidReview() throws Exception {
        // Arrange
        UUID reviewId = UUID.randomUUID();
        Review review = new Review();
        when(reviewRestService.updateRestReview(any(Review.class))).thenReturn(review);

        // Act and Assert
        mockMvc.perform(put("/api/review")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(review)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.userId").doesNotExist()) // Periksa bahwa userId tidak ada dalam respons
        .andExpect(jsonPath("$.productId").doesNotExist()) // Periksa bahwa productId tidak ada dalam respons
        .andExpect(jsonPath("$.rating").value(0)) ;
    }

    @Test
    void testGetReviewsSortedByRatingDesc_NotFound() throws Exception {
        String productId = "product123";

        when(reviewRestService.getAllReviewsSortedByRatingDesc(productId)).thenThrow(new NoSuchElementException());

        mockMvc.perform(get("/api/reviewProduct/sortedByRatingDesc/{id}", productId))
                .andExpect(status().isNotFound());
    }
    
}
