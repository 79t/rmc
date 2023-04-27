package one.suhas.rmc.service;

import one.suhas.rmc.entity.Class;
import one.suhas.rmc.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassService {
    @Autowired
    private ClassRepository classRepository;

    public List<Class> getAllClasses() {
        return classRepository.findAll();
    }

    public Class addClass(String className) {
        return classRepository.save(new Class(className));
    }

}
