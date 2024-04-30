package id.ac.ui.cs.advprog.farrel.restcontroller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;

import id.ac.ui.cs.advprog.farrel.model.Review;
import id.ac.ui.cs.advprog.farrel.restservice.ReviewRestService;

@ExtendWith(MockitoExtension.class)
public class ReviewRestControllerTest {
    Review review1;
    Review review2;
    private MockMvc mockMvc;

    @Mock
    private ReviewRestService reviewRestService;

    @InjectMocks
    private ReviewRestController reviewRestController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(reviewRestController).build();
        review1 = new Review();
        review1.setReviewId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        review1.setUserId("eb558e9f-1c39-460e-8860-12345678");
        review1.setProductId("eb558e9f-1c39-460e-8860-1111111");
        review1.setRating(5);
        review1.setReview("Perfect for layering or as a standalone piece. A staple for any wardrobe.");

        review2 = new Review();
        review2.setReviewId("eb558e9f-1c39-460e-8860-71af6af63bd7");
        review2.setUserId("eb558e9f-1c39-460e-8860-12345678");
        review2.setProductId("eb558e9f-1c39-460e-8860-1111112");
        review2.setRating(2);
        review2.setReview("A staple for any wardrobe.");
    }

    @Test
    public void testRetrieveAllReviews() throws Exception {
        List<Review> reviews = Arrays.asList(review1,
                                            review2);
        when(reviewRestService.retrieveRestAllReview()).thenReturn(reviews);

        mockMvc.perform(get("/api/review/view-all"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].reviewId").value("eb558e9f-1c39-460e-8860-71af6af63bd6"))
               .andExpect(jsonPath("$[1].reviewId").value("eb558e9f-1c39-460e-8860-71af6af63bd7"));
    }

    @Test
    public void testRetrieveReviewNotFound() throws Exception {
        when(reviewRestService.getRestReviewById("1")).thenThrow(new NoSuchElementException());

        mockMvc.perform(get("/api/review/1"))
               .andExpect(status().isNotFound());
    }

    @Test
    public void testAddReview() throws Exception {
        Review newReview = new Review();
        newReview.setReviewId("eb558e9f-1c39-460e-8860-71af6af63bd7");
        newReview.setUserId("eb558e9f-1c39-460e-8860-12345678");
        newReview.setProductId("eb558e9f-1c39-460e-8860-1111112");
        newReview.setRating(2);
        newReview.setReview("A staple for any wardrobe.");
        when(reviewRestService.createRestReview(any(Review.class))).thenReturn(newReview);

        ObjectMapper objectMapper = new ObjectMapper();
        String reviewJson = objectMapper.writeValueAsString(newReview);

        mockMvc.perform(post("/api/review/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(reviewJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.reviewId").value("eb558e9f-1c39-460e-8860-71af6af63bd7"));
    }

    @Test
    public void testUpdateReview() throws Exception {
        Review updatedReview = new Review();
        updatedReview.setReviewId("eb558e9f-1c39-460e-8860-71af6af63bd7");
        updatedReview.setUserId("eb558e9f-1c39-460e-8860-12345678");
        updatedReview.setProductId("eb558e9f-1c39-460e-8860-1111112");
        updatedReview.setRating(5);
        updatedReview.setReview("A staple for any wardrobe.");
        when(reviewRestService.updateRestReview(any(Review.class))).thenReturn(updatedReview);

        ObjectMapper objectMapper = new ObjectMapper();
        String reviewJson = objectMapper.writeValueAsString(updatedReview);

        mockMvc.perform(put("/api/review")
                .contentType(MediaType.APPLICATION_JSON)
                .content(reviewJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rating").value(5));
    }
    @Test
    public void testDeleteReviewSuccess() throws Exception {
        doNothing().when(reviewRestService).restDeleteReview("existing-id");

        mockMvc.perform(delete("/delReview/existing-id"))
            .andExpect(status().isOk())
            .andExpect(content().string("Review berhasil dihapus!"));
    }
    @Test
    public void testDeleteReviewNotFound() throws Exception {
        doThrow(new NoSuchElementException()).when(reviewRestService).restDeleteReview("non-existing-id");

        mockMvc.perform(delete("/delReview/non-existing-id"))
            .andExpect(status().isNotFound())
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
            .andExpect(result -> assertEquals("Review tidak ditemukan", result.getResolvedException().getMessage()));
    }
    @Test
    public void testFindReviewByProductIdSuccess() throws Exception {
        List<Review> reviews = Arrays.asList(review1, review2);
        when(reviewRestService.getRestReviewByProductId("valid-product-id")).thenReturn(reviews);

        mockMvc.perform(get("/reviewProduct/valid-product-id"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].reviewId").value("eb558e9f-1c39-460e-8860-71af6af63bd6"))
            .andExpect(jsonPath("$[1].reviewId").value("eb558e9f-1c39-460e-8860-71af6af63bd7"));
    }

    @Test
    public void testFindReviewByProductIdNotFound() throws Exception {
        when(reviewRestService.getRestReviewByProductId("invalidproductid")).thenThrow(new NoSuchElementException());

        mockMvc.perform(get("/reviewProduct/invalidproductid"))
            .andExpect(status().isNotFound())
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
            .andExpect(result -> assertEquals("Id Review invalid-product-id not found", result.getResolvedException().getMessage()));
    }



    
}
