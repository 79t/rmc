package one.suhas.rmc.repository;

import one.suhas.rmc.entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassRepository extends JpaRepository<Class, Long> {
    List<Class> findByClassName(String className);

    Class findById(long id);
}