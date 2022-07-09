package com.example.nodayst.event.infra;


import com.example.nodayst.event.domain.ReviewEvent;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewEventRepository extends JpaRepository<ReviewEvent, String> {

    boolean existsByPlaceId(String id);

    Optional<ReviewEvent> findByReviewId(String reviewId);

    boolean existsByReviewId(String reviewId);
}
