package one.suhas.rmc.repository;

import one.suhas.rmc.entity.RMCClass;
import org.springframework.data.jpa.repository.JpaRepository;
import one.suhas.rmc.entity.Review;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Review findById(long id);
    List<Review> findAllByRmcClass(RMCClass rmcClass);

}
