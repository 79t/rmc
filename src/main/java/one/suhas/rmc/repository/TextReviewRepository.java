package one.suhas.rmc.repository;


import one.suhas.rmc.entity.RMCClass;
import one.suhas.rmc.entity.TextReview;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TextReviewRepository extends JpaRepository<TextReview, Long> {
    TextReview findById(long id);
    List<TextReview> findAllByRmcClass(RMCClass rmcClass);

    List<TextReview> findAllByApprovedIsFalse(Sort createdDate);

}
