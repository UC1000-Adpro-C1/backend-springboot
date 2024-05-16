
package id.ac.ui.cs.advprog.farrel.strategy;
import id.ac.ui.cs.advprog.farrel.model.Review;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortByRatingStrategy implements SortStrategy {
    @Override
    public List<Review> sort(List<Review> reviews) {
        Collections.sort(reviews, Comparator.comparingInt(Review::getRating));
        return reviews;
    }
}