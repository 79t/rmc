package one.suhas.rmc.service;

import one.suhas.rmc.entity.RMCClass;
import one.suhas.rmc.entity.Review;
import one.suhas.rmc.entity.TextReview;
import one.suhas.rmc.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ClassService {
    private final ClassRepository classRepository;

    @Autowired
    public ClassService(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }
    public List<RMCClass> getAllClasses() {
        return classRepository.findAll();
    }

    public RMCClass addClass(String className) {
        return classRepository.save(new RMCClass(className));
    }

    public RMCClass getById(long id) { return classRepository.findById(id); }

    public Set<Review> getClassReviews(long id) {
        return classRepository.findById(id).getReviews();
    }
    public Set<TextReview> getTextReviews(long id) {
        return classRepository.findById(id).getTextReviews();
    }

    public void deleteClass(long id) {
        classRepository.deleteById(id);
    }

    public void generateClasses() {
        String[] classes = {"AP Chemistry", "AP Calculus AB", "AP Statistics", "AP CSA", "CSIII", "Drone Aviation", "AP Calculus BC", "AP Physics 1"};
        for (String cl: classes) {
            addClass(cl);
        }
    }
}
