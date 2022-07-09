package com.example.nodayst.event.infra;

import com.example.nodayst.event.domain.Review;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, String> {

}
