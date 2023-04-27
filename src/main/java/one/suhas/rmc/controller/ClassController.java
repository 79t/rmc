package one.suhas.rmc.controller;

import one.suhas.rmc.entity.Class;
import one.suhas.rmc.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class ClassController {
    @Autowired
    private ClassService classService;

    @GetMapping("/classes")
    public List<Class> getAllClasses() {
        return classService.getAllClasses();
    }

    @PostMapping("/classes")
    public Class addClass(@RequestParam(name="className") String className) {
        return classService.addClass(className);
    }
}
