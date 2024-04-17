package id.ac.ui.cs.advprog.farrel.model;


public class TaggedReview extends ReviewDecorator {
    private String tag;
    Review review;

    public TaggedReview(Review review, String tag) {
        this.review = review;
        this.tag = tag;
    }
    public String getReviewId() {
        return review.getReviewId();
    }

    public String getUserId() {
        return review.getUserId();
    }

    public String getProductId() {
        return review.getProductId();
    }

    public int getRating() {
        return review.getRating();
    }

    public String getReview() {
        return review.getReview();
    }
    public String getTag() {
        return this.getReview();
    }
}