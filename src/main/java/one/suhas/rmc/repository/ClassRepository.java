package one.suhas.rmc.repository;

import one.suhas.rmc.entity.RMCClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassRepository extends JpaRepository<RMCClass, Long> {
    List<RMCClass> findByClassName(String className);

    RMCClass findById(long id);
}