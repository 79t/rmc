package one.suhas.rmc.service;

import one.suhas.rmc.entity.Review;
import one.suhas.rmc.entity.TextReview;
import one.suhas.rmc.repository.ReviewRepository;
import one.suhas.rmc.repository.TextReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * A class to handle the app logic relating to reviews.
 */
@Service
public class ReviewService {

    /**
     * Automatically injected object to communicate with the database for reviews
     */
    private final ReviewRepository reviewRepository;
    /**
     * Automatically injected object to communicate with the database for reviews with text
     */
    private final TextReviewRepository textReviewRepository;
    /**
     * A queue of unapproved reviews
     */
    private final Queue<TextReview> reviewQueue;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, TextReviewRepository tr) {
        this.reviewRepository = reviewRepository;
        this.textReviewRepository = tr;
        reviewQueue = new LinkedList<TextReview>(
                this.textReviewRepository.findAllByApprovedIsFalse(Sort.by(Sort.Direction.DESC, "createdDate")));
    }

    /**
     * Fetch all reviews in the database
     * @return A list of reviews
     */
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    /**
     * Add a review to the database
     * @param review A review object with the data of the review
     * @return The saved review
     */
    public Review addReview(Review review) {
        return reviewRepository.save(review);
    }


    /**
     * Fetch a review by its ID
     * @param id the ID of the review
     * @return the Review linked to that ID
     */
    public Review getReviewById(long id) { return reviewRepository.findById(id); }
    /**
     * Add a textreview to the database
     * @param textReview A textreview object with the data of the review
     * @return The saved textreview
     */
    public TextReview addReview(TextReview textReview) {
        TextReview r = textReviewRepository.save(textReview);
        reviewQueue.add(r);
        return r;
    }
    /**
     * Fetch all textreviews in the database
     * @return A list of textreviews
     */
    public List<TextReview> getAllTextReviews() {
        return textReviewRepository.findAll();
    }
     /**
     * Fetch a textreview by its ID
     * @param id the ID of the textreview
     * @return the TextReview linked to that ID
     */
    public TextReview getTRById(long id) {
        return textReviewRepository.findById(id);
    }

    /**
     * Get the object at the front of the review queue
     * @return a textreview at the top of the review queue.
     */
    public TextReview peekQueue() {
        return reviewQueue.peek();
    }

    /**
     * Approve the object at the top of the review queue
     * @return the approved textreview
     */
    public TextReview approveTop() {
        TextReview t = reviewQueue.remove();
        t.setApproved(true);
        return textReviewRepository.save(t);
    }

    /**
     * Deny and delete the object of the top of the review queue
     */
    public void denyTop() {
        textReviewRepository.delete(reviewQueue.remove());
    }

}


