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

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final TextReviewRepository textReviewRepository;
    private final Queue<TextReview> reviewQueue;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, TextReviewRepository tr) {
        this.reviewRepository = reviewRepository;
        this.textReviewRepository = tr;
        reviewQueue = new LinkedList<TextReview>(
                this.textReviewRepository.findAllByApprovedIsFalse(Sort.by(Sort.Direction.DESC, "createdDate")));
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }


    public Review addReview(Review review) {
        return reviewRepository.save(review);
    }

    public Review getReviewById(long id) { return reviewRepository.findById(id); }

    public TextReview addReview(TextReview textReview) {
        TextReview r = textReviewRepository.save(textReview);
        reviewQueue.add(r);
        return r;
    }

    public List<TextReview> getAllTextReviews() {
        return textReviewRepository.findAll();
    }
    public TextReview getTRById(long id) {
        return textReviewRepository.findById(id);
    }

    public TextReview peekQueue() {
        return reviewQueue.peek();
    }

    public TextReview approveTop() {
        TextReview t = reviewQueue.remove();
        t.setApproved(true);
        return textReviewRepository.save(t);
    }

    public void denyTop() {
        textReviewRepository.delete(reviewQueue.remove());
    }

}


