package one.suhas.rmc.controller;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.cdimascio.dotenv.Dotenv;
import one.suhas.rmc.entity.*;
import one.suhas.rmc.repository.ClassRepository;
import one.suhas.rmc.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.*;

/**
 * A class for web routes related to reviews
 */

@RestController
public class ReviewController {


    /**
     * Class that stores configuration variables (namely, the OpenAI API Key)
     */
    private final Dotenv dotenv = Dotenv.configure().directory(".").filename(".env").load();
    /**
     * Automatically injected service to handle app logic related to reviews
     */
    private final ReviewService reviewService;
    /**
     * Automatically injected variable to handle database queries related to class (used to lookup classes by their ID)
     */
    private final ClassRepository classRepository;

    @Autowired
    public ReviewController(ReviewService reviewService, ClassRepository classRepository) {
        this.reviewService = reviewService;
        this.classRepository = classRepository;
    }

    /**
     * Adds a review to the database
     * @param review the data of the review
     * @return the review
     */
    @PostMapping("/reviews")
    private Review addReview(@RequestBody Review review) {
        return reviewService.addReview(review);
    }

    /**
     * Get every review on the site
     * @return A list of all reviews in the database
    */
    @GetMapping("/reviews")
    private List<Review> allReviews() {
        return reviewService.getAllReviews();
    }

    /**
     * Fetches a review by it's ID
     * @param id the ID of the review
     * @return the review from the database
     */
    @GetMapping("/reviews/{id}")
    private Review getReview(@PathVariable long id) {
        return reviewService.getReviewById(id);
    }

    /**
     * Adds a review with text
     * @param textReview the content of the review
     * @return the review
     */
    @PostMapping("/textReviews")
    private TextReview addTextReview(@RequestBody TextReview textReview) {
        return reviewService.addReview(textReview);
    }

    /**
     * Fetches all reviews with text
     * @return A list of reviews with text in them
     */
    @GetMapping("/textReviews")
    private List<TextReview> getAllTextReviews() {
       return reviewService.getAllTextReviews();
    }

    /**
     * Fetches a review with text based on it's ID
     * @param id the ID of the review
     * @return the review
     */
    @GetMapping("/textReviews/{id}")
    private TextReview getTextReview(@PathVariable long id) {
        return reviewService.getTRById(id);
    }

    /**
     * A route to render the webpage for the review Queue
     * @return An object containing the name of the webpage for the review queue, and all necessary data needed to render it
     */
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


    /**
     * Fetches the oldest object from the review queue
     * @return a Review
     */
    @GetMapping("/reviewQueue/peek")
    private Review peek() {
        return reviewService.peekQueue();
    }

    /**
     * Denies and deletes the top object on the review queue
     */
    @GetMapping("/reviewQueue/deny")
    private void deny() {
        reviewService.denyTop();
    }

    /**
     * Approves the top review on the review queue, and makes it visible on the website
     * @return the review that will be added
     */
    @GetMapping("/reviewQueue/approve")
    private TextReview approve() {
        return reviewService.approveTop();
    }

    /**
     * Endpoint that recieves reviews without text and adds them to the database
     * @param review the data from the form
     * @return A redirect back to the page of the class the user submitted the review for
     */
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
    /**
     * Endpoint that recieves reviews with text and adds them to the review queue
     * @param review the data from the form
     * @return A notification that their review has been submitted
     */
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

    /**
     * Renders the AI generated reviews page
     * @return the webpage with all needed data
     */
    @GetMapping("/gpt")
    public ModelAndView gptPage() {
        ModelAndView model = new ModelAndView("gpt");
        model.addObject("classes", classRepository.findAll());
        return model;
    }

    /**
     * Endpoint that recieves data from the frontend, and makes a request to OpenAI to get a GPT generated review
     * @param gptForm the form submission from the webpage
     * @return the content of the review, rendered as HTML formatted text
     */
    @PostMapping("/gptForm")
    public String gptForm(GPTForm gptForm) {
        WebClient wc = WebClient.create();
        System.out.println(gptForm.getClassId());
        String prompt = String.format("""
                Please write a review for the class %s in the perspective of a student who has just finished the course. This review will be written based on a set of ratings, as well as some pros and cons. Do not break character while writing this review, it should always be in the perspective of the student. Use complete sentences and proper grammar.\s

                Pros: %s
                Cons: %s
                Review:\s""", gptForm.getClassId(), gptForm.getPros(), gptForm.getCons());
        OpenAIRequest oar = new OpenAIRequest();
        oar.setPrompt(prompt);
        oar.setMax_tokens(256);
        oar.setModel("text-davinci-003");
        oar.setFrequency_penalty(0);
        oar.setPresence_penalty(0);
        oar.setTemperature(0.7);
        oar.setTop_p(1);
        System.out.println("Start post");
        JsonNode jn = wc.post()
                .uri(URI.create("https://api.openai.com/v1/completions"))
                .header("Authorization", "Bearer " + dotenv.get("OPENAI_API_KEY"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(oar), OpenAIRequest.class)
                .retrieve().bodyToMono(JsonNode.class).block();
        String review = jn.get("choices").get(0).get("text").toString();
        String trimmedReview = review.replaceAll("\\n\\n", "");
        return String.format("<h3>Your GPT Review</h3><p>%s</p>", trimmedReview);
    }





}
