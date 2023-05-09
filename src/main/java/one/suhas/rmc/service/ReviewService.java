package one.suhas.rmc.service;

import one.suhas.rmc.entity.Review;
import one.suhas.rmc.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Queue;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Queue<Review> getAllReviewsOrdered() {
//        List<Review> all = reviewRepository.
        return null;
    }
}
