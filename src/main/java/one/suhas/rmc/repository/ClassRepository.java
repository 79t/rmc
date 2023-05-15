package one.suhas.rmc.repository;

import one.suhas.rmc.entity.RMCClass;
import one.suhas.rmc.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Queue;

public interface ClassRepository extends JpaRepository<RMCClass, Long> {
    List<RMCClass> findByClassName(String className);

    RMCClass findById(long id);

}