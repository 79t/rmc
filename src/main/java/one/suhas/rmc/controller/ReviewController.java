package one.suhas.rmc.controller;

import one.suhas.rmc.entity.Review;
import one.suhas.rmc.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Queue;

@RestController
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/reviews")
    private Review addReview(@RequestBody Review review) {
        return reviewService.addReview(review);
    }

    @GetMapping("/reviews")
    private List<Review> allReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/reviewQueue")
    private Queue<Review> reviewQueue() { return reviewService.getAllReviewsOrdered(); }

    @GetMapping("/reviews/{id}")
    private Review getReview(@PathVariable long id) {
        return reviewService.getReviewById(id);
    }


}
