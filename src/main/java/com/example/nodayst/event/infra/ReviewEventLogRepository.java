package com.example.nodayst.event.infra;

import com.example.nodayst.event.domain.ReviewEventLog;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewEventLogRepository extends JpaRepository<ReviewEventLog, String> {

    public List<ReviewEventLog> findAllByUserIdOrderByIdDesc(String userId);
}
