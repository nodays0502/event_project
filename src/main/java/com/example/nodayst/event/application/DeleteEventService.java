package com.example.nodayst.event.application;

import com.example.nodayst.common.model.Score;
import com.example.nodayst.common.exception.event.NotFoundEventException;
import com.example.nodayst.common.exception.review.NotFoundReviewException;
import com.example.nodayst.common.exception.user.NotFoundUserException;
import com.example.nodayst.event.domain.ReviewEvent;
import com.example.nodayst.event.domain.Review;
import com.example.nodayst.event.domain.ReviewEventLog;
import com.example.nodayst.event.domain.User;
import com.example.nodayst.event.infra.ReviewEventLogRepository;
import com.example.nodayst.event.infra.ReviewEventRepository;
import com.example.nodayst.event.infra.ReviewRepository;
import com.example.nodayst.event.infra.UserRepository;
import com.example.nodayst.event.ui.dto.PostEventRequest;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class DeleteEventService {

    private final ReviewEventRepository reviewEventRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    private final ReviewEventLogRepository reviewEventLogRepository;

    public void deleteEvent(PostEventRequest postEventRequest) {

        Review review = reviewRepository.findById(postEventRequest.getReviewId())
            .orElseThrow(() -> {
                throw new NotFoundReviewException();
            });

        ReviewEvent reviewEvent = reviewEventRepository.findByReviewId(postEventRequest.getReviewId())
            .orElseThrow(() -> {
                throw new NotFoundEventException();
            });

        User user = userRepository.findById(postEventRequest.getUserId()).orElseThrow(() -> {
            throw new NotFoundUserException();
        });

        user.addScore(Score.changeSign(reviewEvent.getReviewScore().calculateScore()));
        reviewEventRepository.delete(reviewEvent);
        reviewRepository.delete(review);

        // 증감 이력 저장
        ReviewEventLog reviewEventLog = new ReviewEventLog(user.getId(),
            postEventRequest.getAction(), Score.changeSign(reviewEvent.getReviewScore().calculateScore()));
        reviewEventLogRepository.save(reviewEventLog);

    }
}
