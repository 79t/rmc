package one.suhas.rmc.controller;

import one.suhas.rmc.entity.*;
import one.suhas.rmc.service.ClassService;
import one.suhas.rmc.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.ModelAndViewResolver;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
public class ClassController {
    private final ClassService classService;
    @Autowired
    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @GetMapping("/classes")
    public List<RMCClass> getAllClasses() {
        return classService.getAllClasses();
    }


    @PostMapping("/classes")
    public RMCClass addClass(@RequestParam(name="className") String className) {
        return classService.addClass(className);
    }

    @GetMapping("/classes/{class}")
    public RMCClass getClassById(@PathVariable("class") long id) {
        return classService.getById(id);
    }

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

    @GetMapping("/classes/{class}/reviews")
    public Set<Review> getClassReviews(@PathVariable("class") long id) { return classService.getClassReviews(id); }

    @GetMapping("/classes/{class}/textReviews")
    public Set<TextReview> getClassTextReviews(@PathVariable("class") long id) { return classService.getTextReviews(id); }

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

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView model = new ModelAndView("index");
        model.addObject("classes", classService.getAllClasses());
        return model;
    }


}
