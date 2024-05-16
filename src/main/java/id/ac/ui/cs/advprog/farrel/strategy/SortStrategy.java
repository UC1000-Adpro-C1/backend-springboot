package id.ac.ui.cs.advprog.farrel.strategy;

import java.util.List;

import id.ac.ui.cs.advprog.farrel.model.Review;

public interface SortStrategy {
    List<Review> sort(List<Review> listings);
}