package com.example.nodayst.event.application;

import com.example.nodayst.event.domain.ReviewEventLog;
import com.example.nodayst.event.infra.ReviewEventLogRepository;
import com.example.nodayst.event.ui.dto.GetUserScoreHistoryResponse;
import com.example.nodayst.event.ui.dto.UserScoreHistory;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class GetUserScoreHistoryService {

    private final ReviewEventLogRepository reviewEventLogRepository;

    public List<UserScoreHistory> getUserScoreHistory(String userId){
        List<ReviewEventLog> ReviewEventLogs = reviewEventLogRepository.findAllByUserIdOrderByIdDesc(userId);
        return ReviewEventLogs.stream()
            .map(UserScoreHistory::ReviewEventLogMapToUserScoreHistory).collect(
                Collectors.toList());
    }
}
