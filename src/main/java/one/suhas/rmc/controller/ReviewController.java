package one.suhas.rmc.controller;

import one.suhas.rmc.entity.Review;
import one.suhas.rmc.entity.ReviewSubmission;
import one.suhas.rmc.entity.TextReview;
import one.suhas.rmc.entity.TextReviewSubmission;
import one.suhas.rmc.repository.ClassRepository;
import one.suhas.rmc.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Queue;

@RestController
public class ReviewController {
    private final ReviewService reviewService;
    private final ClassRepository classRepository;

    @Autowired
    public ReviewController(ReviewService reviewService, ClassRepository classRepository) {
        this.reviewService = reviewService;
        this.classRepository = classRepository;
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
        TextReview t = reviewService.peekQueue();
        model.addObject("review", t);
        if (t == null) {
            return new ModelAndView("noReviews");
        }
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
    public ModelAndView submitReviewForm(@ModelAttribute ReviewSubmission review) {
        Review re = new Review(
                classRepository.findById(review.getRmcClassId()),
                review.getInteresting(),
                review.getHard(),
                review.getHomework(),
                review.getExams()
        );
        reviewService.addReview(re);
        return new ModelAndView(String.format("redirect:/view/classes/%d", review.getRmcClassId()));
    }

    @PostMapping("/textReviewForm")
    public String submitTextReviewForm(@ModelAttribute TextReviewSubmission review) {
        TextReview tr = new TextReview(
                classRepository.findById(review.getRmcClassId()),
                review.getInteresting(),
                review.getHard(),
                review.getHomework(),
                review.getExams(),
                review.getText()
        );
        reviewService.addReview(tr);
        return "Thank you for your submission! Your review has been added to the queue";
    }



}
