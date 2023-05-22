package one.suhas.rmc.controller;

import one.suhas.rmc.entity.*;
import one.suhas.rmc.service.ClassService;
import one.suhas.rmc.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Defines web routes for functions relating to courses.
 * @author Suhas
 */

@RestController
public class ClassController {

    /**
     * The service that handles app logic for class routes.
     * This will be automatically injected into the class due to the @Autowired annotation in the constructor.
     */
    private final ClassService classService;
    @Autowired
    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    /**
     * Helper route to generate some test classes
     * @return A redirect to the index page
     */
    @GetMapping("/generateClasses")
    public ModelAndView generateClasses() {
        classService.generateClasses();
        return new ModelAndView("redirect:/");
    }

    /**
     * An API route to return a JSON object of all classes in the database
     * @return A list of all classes in the database
     */
    @GetMapping("/classes")
    public List<RMCClass> getAllClasses() {
        return classService.getAllClasses();
    }

    /**
     * Deletes a class from the database based on the provided ID, and returns it
     * @param id The ID of the class to delete
     * @return The deleted class
     */
    @DeleteMapping("/classes/{id}")
    public String deleteClass(@PathVariable("id") long id) {
        classService.deleteClass(id);
        return "ok";
    }

    /**
     * API route to add a class to the database
     * @param className The name of the class to add
     * @return The class object
     */
    @PostMapping("/classes")
    public RMCClass addClass(@RequestParam(name="className") String className) {
        return classService.addClass(className);
    }

    /**
     * An API route to fetch a class given an ID
     * @param id the ID of the class to fetch
     * @return The fetched class
     */
    @GetMapping("/classes/{class}")
    public RMCClass getClassById(@PathVariable("class") long id) {
        return classService.getById(id);
    }

    /**
     * Renders a webpage for a given class, where users can view and add reviews
     * @param id the ID of the class to render
     * @return An object containing a link to the webpage for Spring to render, and the objects it embeds in the page
     */
    @GetMapping("/view/classes/{class}")
    public ModelAndView viewClass(@PathVariable("class") long id) {
        RMCClass c = classService.getById(id);
        Set<Review> reviews = c.getReviews();
        List<Review> filteredReviews = new ArrayList<Review>();
        List<TextReview> trs = new ArrayList<TextReview>();
        for (Review r: reviews) {
            if (r instanceof TextReview) {
                if (((TextReview) r).getApproved()) trs.add((TextReview) r);
                else continue;
            }
            filteredReviews.add(r);
        }


        ModelAndView model = new ModelAndView("classView");
        model.addObject("class", c);
        model.addObject("reviews", filteredReviews);
        model.addObject("trs", trs);
        model.addObject("starValueToString", Constants.starValueToString);
        return model;
    }

    /**
     * Fetches the reviews for a given class
     * @param id The ID of the class to fetch reviews for
     * @return A list of reviews.
     */
    @GetMapping("/classes/{class}/reviews")
    public Set<Review> getClassReviews(@PathVariable("class") long id) { return classService.getClassReviews(id); }


    /**
     * Fetches reviews with text for a given class
     * @param id the ID of the class to fetch reviews for
     * @return A list of reviews.
     */
    @GetMapping("/classes/{class}/textReviews")
    public Set<TextReview> getClassTextReviews(@PathVariable("class") long id) { return classService.getTextReviews(id); }

    /**
     * Renders a webpage where users can add ratings (no text)
     * @param id the ID of the class that the user will add a rating for
     * @return the webpage with all necessary objects for the webpage
     */
    @GetMapping("/view/classes/{class}/addReview")
    public ModelAndView addReview(@PathVariable("class") long id) {
        ModelAndView model = new ModelAndView("addReview");
        ReviewSubmission r = new ReviewSubmission();
        RMCClass cla = classService.getById(id);
        r.setRmcClassId(cla.getId());
        model.addObject("review", r);
        model.addObject("the_class", cla);
        model.addObject("the_id", id);
        return model;
    }

    /**
     * Renders a webpage where users can add reviews (has text)
     * @param id the ID of the class that the user will add a rating for
     * @return the webpage with all necessary objects for the webpage
     */
    @GetMapping("/view/classes/{class}/addTextReview")
    public ModelAndView addTextReview(@PathVariable("class") long id) {
        ModelAndView model = new ModelAndView("addTextReview");
        TextReviewSubmission r = new TextReviewSubmission();
        RMCClass cla = classService.getById(id);
        r.setRmcClassId(cla.getId());
        model.addObject("review", r);
        model.addObject("the_class", cla);
        model.addObject("the_id", id);
        return model;
    }

    /**
     * Renders the main webpage with a list of classes
     * @return The main webpage object
     */
    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView model = new ModelAndView("index");
        model.addObject("classes", classService.getAllClasses());
        return model;
    }


}
