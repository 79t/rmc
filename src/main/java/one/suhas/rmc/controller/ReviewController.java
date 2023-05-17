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

@RestController
public class ReviewController {

    private final Dotenv dotenv = Dotenv.configure().directory(".").filename(".env").load();
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

    @GetMapping("/gpt")
    public ModelAndView gptPage() {
        ModelAndView model = new ModelAndView("gpt");
        model.addObject("classes", classRepository.findAll());
        return model;
    }

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
