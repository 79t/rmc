package one.suhas.rmc.controller;

import one.suhas.rmc.entity.RMCClass;
import one.suhas.rmc.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

}
