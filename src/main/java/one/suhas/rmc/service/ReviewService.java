package one.suhas.rmc.service;

import one.suhas.rmc.entity.Review;
import one.suhas.rmc.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final Queue<Review> reviewQueue;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
        reviewQueue = new LinkedList<Review>(this.reviewRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate")));
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Queue<Review> getAllReviewsOrdered() {
        return reviewQueue;
    }


    public Review addReview(Review review) {
        return reviewRepository.save(review);
    }

    public Review getReviewById(long id) { return reviewRepository.findById(id); }
}
