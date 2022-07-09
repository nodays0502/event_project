package com.example.nodayst.event.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.nodayst.common.exception.event.AlreadyReviewHasEvent;
import com.example.nodayst.common.exception.place.NotFoundPlaceException;
import com.example.nodayst.common.exception.user.NotFoundUserException;
import com.example.nodayst.common.model.Score;
import com.example.nodayst.event.domain.ReviewEventAction;
import com.example.nodayst.event.domain.User;
import com.example.nodayst.event.infra.PlaceRepository;
import com.example.nodayst.event.infra.ReviewEventLogRepository;
import com.example.nodayst.event.infra.ReviewEventRepository;
import com.example.nodayst.event.infra.ReviewRepository;
import com.example.nodayst.event.infra.UserRepository;
import com.example.nodayst.event.ui.dto.PostEventRequest;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AddEventServiceTest {

    private AddEventService addEventService;

    @Mock
    private PlaceRepository placeRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ReviewEventRepository reviewEventRepository;

    private CalculateReviewScore calculateReviewScore;

    @Mock
    private ReviewEventLogRepository reviewEventLogRepository;

    @BeforeEach
    void init() {
        calculateReviewScore = new CalculateReviewScore(reviewEventRepository);
        addEventService = new AddEventService(placeRepository,
            reviewRepository, userRepository, reviewEventRepository,
            calculateReviewScore, reviewEventLogRepository);
    }

    @Test
    public void addEventFailNotFoundPlaceTest() {
        String reviewId = "reviewId";
        Set<String> attachedPhotoIds = new HashSet<>();
        attachedPhotoIds.add("1");
        attachedPhotoIds.add("2");
        String content = " 컨텐츠";
        String userId = "userId";
        String placeId = "placeId";
        PostEventRequest postEventRequest = new PostEventRequest("REVIEW", ReviewEventAction.ADD,
            reviewId,
            content, attachedPhotoIds, userId, placeId);

        when(placeRepository.existsById(any())).thenReturn(false);

        assertThrows(NotFoundPlaceException.class, () -> {
            addEventService.addEvent(postEventRequest);
        });
    }

    @Test
    public void addEventAlreadyReviewHasEventTest() {
        String reviewId = "reviewId";
        Set<String> attachedPhotoIds = new HashSet<>();
        attachedPhotoIds.add("1");
        attachedPhotoIds.add("2");
        String content = " 컨텐츠";
        String userId = "userId";
        String placeId = "placeId";
        PostEventRequest postEventRequest = new PostEventRequest("REVIEW", ReviewEventAction.ADD,
            reviewId,
            content, attachedPhotoIds, userId, placeId);

        when(placeRepository.existsById(any())).thenReturn(true);
        when(reviewEventRepository.existsByReviewId(any())).thenReturn(true);

        assertThrows(AlreadyReviewHasEvent.class, () -> {
            addEventService.addEvent(postEventRequest);
        });
    }

    @Test
    public void addEventNotFoundUserExceptionTest() {
        String reviewId = "reviewId";
        Set<String> attachedPhotoIds = new HashSet<>();
        attachedPhotoIds.add("1");
        attachedPhotoIds.add("2");
        String content = " 컨텐츠";
        String userId = "userId";
        String placeId = "placeId";
        PostEventRequest postEventRequest = new PostEventRequest("REVIEW", ReviewEventAction.ADD,
            reviewId,
            content, attachedPhotoIds, userId, placeId);

        when(placeRepository.existsById(any())).thenReturn(true);
        when(reviewEventRepository.existsByReviewId(any())).thenReturn(false);
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NotFoundUserException.class, () -> {
            addEventService.addEvent(postEventRequest);
        });
    }

    @Test
    public void addEventSuccessTest() {
        String reviewId = "reviewId";
        Set<String> attachedPhotoIds = new HashSet<>();
        attachedPhotoIds.add("1");
        attachedPhotoIds.add("2");
        String content = " 컨텐츠";
        String userId = "userId";
        String placeId = "placeId";
        User user = new User(userId, Score.ONE);
        user.addScore(Score.ZERO);
        PostEventRequest postEventRequest = new PostEventRequest("REVIEW", ReviewEventAction.ADD,
            reviewId,
            content, attachedPhotoIds, userId, placeId);

        when(placeRepository.existsById(any())).thenReturn(true);
        when(reviewEventRepository.existsByReviewId(any())).thenReturn(false);
        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        addEventService.addEvent(postEventRequest);
    }
}