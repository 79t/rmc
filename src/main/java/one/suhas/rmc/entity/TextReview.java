package one.suhas.rmc.entity;

import jakarta.persistence.Entity;
import one.suhas.rmc.utils.StarValue;

@Entity
public class TextReview extends Review {
    private String reviewText;
    private boolean approved = false;

    public TextReview(long id, RMCClass rmcClass, StarValue interesting, StarValue hard, StarValue homework, StarValue exams, String reviewText) {
        super(id, rmcClass, interesting, hard, homework, exams);
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
