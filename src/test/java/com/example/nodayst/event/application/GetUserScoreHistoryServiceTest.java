package com.example.nodayst.event.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.example.nodayst.common.model.Score;
import com.example.nodayst.event.domain.ReviewEventAction;
import com.example.nodayst.event.domain.ReviewEventLog;
import com.example.nodayst.event.infra.ReviewEventLogRepository;
import com.example.nodayst.event.ui.dto.UserScoreHistory;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetUserScoreHistoryServiceTest {

    private GetUserScoreHistoryService getUserScoreHistoryService;

    @Mock
    private ReviewEventLogRepository reviewEventLogRepository;

    @BeforeEach
    public void init() {
        getUserScoreHistoryService = new GetUserScoreHistoryService(reviewEventLogRepository);
    }

    @Test
    public void getGetUserScoreHistorySuccessTest() {
        String userId = "userId";
        List<ReviewEventLog> reviewEventLogs = new LinkedList<>();
        ReviewEventLog eventLog2 = new ReviewEventLog(2,userId, ReviewEventAction.MOD, new Score(2));
        ReviewEventLog eventLog1 = new ReviewEventLog(1,userId, ReviewEventAction.ADD, Score.ONE);
        reviewEventLogs.add(eventLog2);
        reviewEventLogs.add(eventLog1);

        when(reviewEventLogRepository.findAllByUserIdOrderByIdDesc(userId)).thenReturn(reviewEventLogs);
        List<UserScoreHistory> userScoreHistories = getUserScoreHistoryService.getUserScoreHistory(
            userId);

        UserScoreHistory userScoreHistory1 = new UserScoreHistory(ReviewEventAction.MOD,
            2L);
        UserScoreHistory userScoreHistory2 = new UserScoreHistory(ReviewEventAction.ADD,
            1L);
        assertEquals(userScoreHistory1,userScoreHistories.get(0));
        assertEquals(userScoreHistory2,userScoreHistories.get(1));

    }
}