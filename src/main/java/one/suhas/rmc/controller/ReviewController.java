package one.suhas.rmc.controller;

import one.suhas.rmc.entity.Review;
import one.suhas.rmc.entity.TextReview;
import one.suhas.rmc.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

    @GetMapping("/reviews/{id}")
    private Review getReview(@PathVariable long id) {
        return reviewService.getReviewById(id);
    }

    @PostMapping("/textReviews")
    private TextReview addTextReview(@RequestBody TextReview textReview) {
        return reviewService.addReview(textReview);
    }

    @GetMapping("/textReviews")
    private List<TextReview> getAllTextReviews() {
       return reviewService.getAllTextReviews();
    }

    @GetMapping("/textReviews/{id}")
    private TextReview getTextReview(@PathVariable long id) {
        return reviewService.getTRById(id);
    }

    @GetMapping("view/reviewQueue")
    private ModelAndView reviewQueue() {
        ModelAndView model = new ModelAndView("reviewQueue");
        model.addObject("review", reviewService.peekQueue());
        return model;
    }

    @GetMapping("/reviewQueue/peek")
    private Review peek() {
        return reviewService.peekQueue();
    }

    @GetMapping("/reviewQueue/deny")
    private void deny() {
        reviewService.denyTop();
    }

    @GetMapping("/reviewQueue/approve")
    private TextReview approve() {
        return reviewService.approveTop();
    }

    @PostMapping("/reviewForm")
    public String submitReviewForm(@ModelAttribute Review review) {
        reviewService.addReview(review);
        return "done";
    }



}
