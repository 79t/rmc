package one.suhas.rmc.entity;

import jakarta.persistence.Entity;
import one.suhas.rmc.utils.StarValue;


/**
 * A review, but with text.
 * @see Review
 */
@Entity
public class TextReview extends Review {

    /**
     * The text of the review
     */
    private String reviewText;
    /**
     * Whether the review has been approved or not. This defaults to false, as all reviews must be manually verified
     * before being approved/visible in the website
     */
    private boolean approved = false;

    public TextReview(RMCClass rmcClass, StarValue interesting, StarValue hard, StarValue homework, StarValue exams, String reviewText) {
        super(rmcClass, interesting, hard, homework, exams);
        this.reviewText = reviewText;
        this.approved = false;
    }

    public TextReview() {
        super();
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String n) {
        reviewText = n;
    }

    public boolean getApproved() { return approved; }
    public void setApproved(boolean b) { this.approved = b; }
}
