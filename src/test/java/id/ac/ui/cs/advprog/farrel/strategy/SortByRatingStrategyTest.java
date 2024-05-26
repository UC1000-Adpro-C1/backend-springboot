package id.ac.ui.cs.advprog.farrel.strategy;

import id.ac.ui.cs.advprog.farrel.model.Review;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SortByRatingStrategyTest {

    private Review createReview(int rating, String comment) {
        return new Review(UUID.randomUUID(), "user123", "product123", rating, comment);
    }

    @Test
    public void testSort_HappyPath() {
        SortStrategy sortStrategy = new SortByRatingStrategy();

        List<Review> reviews = new ArrayList<>();
        reviews.add(createReview(5, "Good product"));
        reviews.add(createReview(3, "Average product"));
        reviews.add(createReview(1, "Bad product"));

        List<Review> sortedReviews = sortStrategy.sort(reviews);

        assertEquals(1, sortedReviews.get(0).getRating());
        assertEquals(3, sortedReviews.get(1).getRating());
        assertEquals(5, sortedReviews.get(2).getRating());
    }

    @Test
    public void testSort_UnhappyPath_NullList() {
        SortStrategy sortStrategy = new SortByRatingStrategy();

        assertThrows(NullPointerException.class, () -> {
            sortStrategy.sort(null);
        });
    }

    @Test
    public void testSort_UnhappyPath_EmptyList() {
        SortStrategy sortStrategy = new SortByRatingStrategy();

        List<Review> reviews = new ArrayList<>();
        List<Review> sortedReviews = sortStrategy.sort(reviews);

        assertEquals(0, sortedReviews.size());
    }

    @Test
    public void testSort_CornerCase_SingleReview() {
        SortStrategy sortStrategy = new SortByRatingStrategy();

        List<Review> reviews = new ArrayList<>();
        reviews.add(createReview(3, "Average product"));

        List<Review> sortedReviews = sortStrategy.sort(reviews);

        assertEquals(1, sortedReviews.size());
        assertEquals(3, sortedReviews.get(0).getRating());
    }

    @Test
    public void testSort_CornerCase_SameRating() {
        SortStrategy sortStrategy = new SortByRatingStrategy();

        List<Review> reviews = new ArrayList<>();
        reviews.add(createReview(3, "Average product 1"));
        reviews.add(createReview(3, "Average product 2"));
        reviews.add(createReview(3, "Average product 3"));

        List<Review> sortedReviews = sortStrategy.sort(reviews);

        assertEquals(3, sortedReviews.size());
        assertEquals(3, sortedReviews.get(0).getRating());
        assertEquals(3, sortedReviews.get(1).getRating());
        assertEquals(3, sortedReviews.get(2).getRating());
    }

    @Test
    public void testSort_CornerCase_ExtremeValues() {
        SortStrategy sortStrategy = new SortByRatingStrategy();

        List<Review> reviews = new ArrayList<>();
        reviews.add(createReview(Integer.MAX_VALUE, "Extremely good product"));
        reviews.add(createReview(Integer.MIN_VALUE, "Extremely bad product"));
        reviews.add(createReview(0, "Neutral product"));

        List<Review> sortedReviews = sortStrategy.sort(reviews);

        assertEquals(Integer.MIN_VALUE, sortedReviews.get(0).getRating());
        assertEquals(0, sortedReviews.get(1).getRating());
        assertEquals(Integer.MAX_VALUE, sortedReviews.get(2).getRating());
    }

    @Test
    public void testSort_CornerCase_MixedNullReviews() {
        SortStrategy sortStrategy = new SortByRatingStrategy();

        List<Review> reviews = new ArrayList<>();
        reviews.add(createReview(5, "Good product"));
        reviews.add(null);
        reviews.add(createReview(3, "Average product"));

        assertThrows(NullPointerException.class, () -> {
            sortStrategy.sort(reviews);
        });
    }
}
