package id.ac.ui.cs.advprog.farrel.model;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class Review{
    private String reviewId;
    private String userId;
    private String productId;
    private int rating;
    private String review;
}