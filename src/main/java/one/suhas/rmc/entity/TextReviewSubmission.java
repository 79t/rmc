package one.suhas.rmc.entity;

import one.suhas.rmc.utils.StarValue;

public class TextReviewSubmission extends ReviewSubmission {
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
