package one.suhas.rmc.controller;

import one.suhas.rmc.entity.Class;
import one.suhas.rmc.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClassController {
    private ClassService classService;

    @Autowired
    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @GetMapping("/classes")
    public List<Class> getAllClasses() {
        return classService.getAllClasses();
    }


    @PostMapping("/classes")
    public Class addClass(@RequestParam(name="className") String className) {
        return classService.addClass(className);
    }

    @GetMapping("/classes/{class}")
    public Class getClassById(@PathVariable("class") long id) {
        return classService.getById(id);
    }

}
