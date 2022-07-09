package com.example.nodayst.event.application;

import com.example.nodayst.common.model.ReviewScore;
import com.example.nodayst.common.model.Score;
import com.example.nodayst.common.exception.event.NotFoundEventException;
import com.example.nodayst.common.exception.user.NotFoundUserException;
import com.example.nodayst.event.domain.ReviewEvent;
import com.example.nodayst.event.domain.ReviewEventLog;
import com.example.nodayst.event.domain.User;
import com.example.nodayst.event.infra.ReviewEventLogRepository;
import com.example.nodayst.event.infra.ReviewEventRepository;
import com.example.nodayst.event.infra.UserRepository;
import com.example.nodayst.event.ui.dto.PostEventRequest;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ModifyEventService {

    private final ReviewEventRepository reviewEventRepository;
    private final CalculateReviewScore calculateReviewScore;
    private final UserRepository userRepository;

    private final ReviewEventLogRepository reviewEventLogRepository;

    public void modifyEvent(PostEventRequest postEventRequest) {
        ReviewEvent reviewEvent = reviewEventRepository.findByReviewId(postEventRequest.getReviewId())
            .orElseThrow(() -> {
                throw new NotFoundEventException();
            });

        ReviewScore reviewScore = calculateReviewScore.calculateReviewScore(
            reviewEvent.getReviewScore().getFirstReviewScore(),
            postEventRequest.getAttachedPhotoIds().size(),
            postEventRequest.getContent().length());
        Score gap = reviewScore.calculateScore().minus(reviewEvent.getReviewScore().calculateScore());
        reviewEvent.changeScore(reviewScore);

        User user = userRepository.findById(postEventRequest.getUserId()).orElseThrow(() -> {
            throw new NotFoundUserException();
        });

        user.addScore(gap);

        // 증감 이력 저장
        ReviewEventLog reviewEventLog = new ReviewEventLog(user.getId(),
            postEventRequest.getAction(), gap);
        reviewEventLogRepository.save(reviewEventLog);
    }
}
