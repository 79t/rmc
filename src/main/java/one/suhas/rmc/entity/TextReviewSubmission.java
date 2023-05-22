package one.suhas.rmc.entity;

import one.suhas.rmc.utils.StarValue;


/**
 * A class to contain the form data for when a user submits a text review. Since HTML forms cannot contain java objects,
 * this is used as an intermediary between the HTML form and the TextReview entity object.
 * @see TextReview
 * @see ReviewSubmission
 */
public class TextReviewSubmission extends ReviewSubmission {
    /**
     * The text from the HTML form for the review
     */
    private String text;

    public TextReviewSubmission(StarValue hard, StarValue interesting, StarValue homework, StarValue exams, long rmcClassId, String text) {
        super(hard, interesting, homework, exams, rmcClassId);
        this.text = text;
    }

    public TextReviewSubmission(String text) {
        this.text = text;
    }

    public TextReviewSubmission() {

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }



}
