package one.suhas.rmc.service;

import one.suhas.rmc.entity.RMCClass;
import one.suhas.rmc.entity.Review;
import one.suhas.rmc.entity.TextReview;
import one.suhas.rmc.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * The app logic for anything related to classes
 */
@Service
public class ClassService {
    /**
     * Automatically injected variable to communicate with the database for class lookups/additions/deletions
     */
    private final ClassRepository classRepository;

    @Autowired
    public ClassService(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    /**
     * Fetches all classes in the database
     * @return A list of all classes
     */
    public List<RMCClass> getAllClasses() {
        return classRepository.findAll();
    }

    /**
     * Adds a class to the database
     * @param className the name of the class
     * @return the class
     */
    public RMCClass addClass(String className) {
        return classRepository.save(new RMCClass(className));
    }

    /**
     * Fetches a class by its ID
     * @param id the ID of the class
     * @return the class with that ID
     */

    public RMCClass getById(long id) { return classRepository.findById(id); }

    /**
     * Fetch the reviews of a class
     * @param id the ID of the class
     * @return a Set with the reviews of that class
     */
    public Set<Review> getClassReviews(long id) {
        return classRepository.findById(id).getReviews();
    }
    /**
     * Fetch the reviews with text of a class
     * @param id the ID of the class
     * @return a Set with the textreviews of that class
     */
    public Set<TextReview> getTextReviews(long id) {
        return classRepository.findById(id).getTextReviews();
    }

    /**
     * Deletes a class.
     * @param id the ID of the class.
     */
    public void deleteClass(long id) {
        classRepository.deleteById(id);
    }

    /**
     * Generates some classes, and adds them to the database.
     */
    public void generateClasses() {
        String[] classes = {"AP Chemistry", "AP Calculus AB", "AP Statistics", "AP CSA", "CSIII", "Drone Aviation", "AP Calculus BC", "AP Physics 1"};
        for (String cl: classes) {
            addClass(cl);
        }
    }
}
