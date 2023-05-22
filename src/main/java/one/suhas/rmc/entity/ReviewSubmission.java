package one.suhas.rmc.entity;

import one.suhas.rmc.utils.StarValue;

/**
 * A class to contain the form data for when a user submits a review. Since HTML forms cannot contain java objects,
 * this is used as an intermediary between the HTML form and the Review entity object.
 * @see Review
 */

public class ReviewSubmission {
    private StarValue hard;
    private StarValue interesting;
    private StarValue homework;
    private StarValue exams;

    private long rmcClassId;

    public ReviewSubmission(StarValue hard, StarValue interesting, StarValue homework, StarValue exams, long rmcClassId) {
        this.hard = hard;
        this.interesting = interesting;
        this.homework = homework;
        this.exams = exams;
        this.rmcClassId = rmcClassId;
    }
    public ReviewSubmission() {}

    public StarValue getHard() {
        return hard;
    }

    public void setHard(StarValue hard) {
        this.hard = hard;
    }

    public StarValue getInteresting() {
        return interesting;
    }

    public void setInteresting(StarValue interesting) {
        this.interesting = interesting;
    }

    public StarValue getHomework() {
        return homework;
    }

    public void setHomework(StarValue homework) {
        this.homework = homework;
    }

    public StarValue getExams() {
        return exams;
    }

    public void setExams(StarValue exams) {
        this.exams = exams;
    }

    public long getRmcClassId() {
        return rmcClassId;
    }

    public void setRmcClassId(long rmcClassId) {
        this.rmcClassId = rmcClassId;
    }

    @Override
    public String toString() {
        return "ReviewSubmission{" +
                "hard=" + hard +
                ", interesting=" + interesting +
                ", homework=" + homework +
                ", exams=" + exams +
                ", rmcClassId=" + rmcClassId +
                '}';
    }
}
