package com.example.nodayst.event.application;

import com.example.nodayst.common.exception.event.AlreadyReviewHasEvent;
import com.example.nodayst.common.model.Image;
import com.example.nodayst.common.model.ReviewScore;
import com.example.nodayst.common.exception.place.NotFoundPlaceException;
import com.example.nodayst.common.exception.user.NotFoundUserException;
import com.example.nodayst.event.domain.ReviewEvent;
import com.example.nodayst.event.domain.Review;
import com.example.nodayst.event.domain.ReviewEventLog;
import com.example.nodayst.event.domain.User;
import com.example.nodayst.event.infra.PlaceRepository;
import com.example.nodayst.event.infra.ReviewEventLogRepository;
import com.example.nodayst.event.infra.ReviewEventRepository;
import com.example.nodayst.event.infra.ReviewRepository;
import com.example.nodayst.event.infra.UserRepository;
import com.example.nodayst.event.ui.dto.PostEventRequest;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AddEventService {

    private final PlaceRepository placeRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ReviewEventRepository reviewEventRepository;
    private final CalculateReviewScore calculateReviewScore;

    private final ReviewEventLogRepository reviewEventLogRepository;

    public void addEvent(PostEventRequest postEventRequest) {
        if (!placeRepository.existsById(postEventRequest.getPlaceId())) {
            throw new NotFoundPlaceException();
        }
        if(reviewEventRepository.existsByReviewId(postEventRequest.getReviewId())){
            throw new AlreadyReviewHasEvent();
        }

        Set<Image> images = postEventRequest.getAttachedPhotoIds().stream().map((o) -> {
            return new Image(o);
        }).collect(
            Collectors.toSet());

        String reviewId = postEventRequest.getReviewId();
        Review review = new Review(reviewId, postEventRequest.getContent(), images,
            postEventRequest.getUserId(),
            postEventRequest.getPlaceId());

        ReviewScore reviewScore = calculateReviewScore.calculateReviewScore(review.getPlaceId(),
            review.getImages().size(),
            review.getContent().length());

        reviewRepository.save(review);
        String reviewEventId = String.valueOf(UUID.randomUUID());

        ReviewEvent reviewEvent = new ReviewEvent(reviewEventId, reviewId, postEventRequest.getPlaceId(),
            reviewScore);
        reviewEventRepository.save(reviewEvent);

        User user = userRepository.findById(postEventRequest.getUserId()).orElseThrow(() -> {
            throw new NotFoundUserException();
        });
        user.addScore(reviewScore.calculateScore());

        // 증감 이력 저장
        ReviewEventLog reviewEventLog = new ReviewEventLog(user.getId(),
            postEventRequest.getAction(), reviewScore.calculateScore());
        reviewEventLogRepository.save(reviewEventLog);
    }
}
