package com.example.nodayst.event.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.nodayst.common.exception.event.NotFoundEventException;
import com.example.nodayst.common.exception.place.NotFoundPlaceException;
import com.example.nodayst.common.exception.review.NotFoundReviewException;
import com.example.nodayst.common.exception.user.NotFoundUserException;
import com.example.nodayst.common.model.Image;
import com.example.nodayst.common.model.ReviewScore;
import com.example.nodayst.common.model.Score;
import com.example.nodayst.event.domain.Review;
import com.example.nodayst.event.domain.ReviewEvent;
import com.example.nodayst.event.domain.ReviewEventAction;
import com.example.nodayst.event.domain.User;
import com.example.nodayst.event.infra.ReviewEventLogRepository;
import com.example.nodayst.event.infra.ReviewEventRepository;
import com.example.nodayst.event.infra.ReviewRepository;
import com.example.nodayst.event.infra.UserRepository;
import com.example.nodayst.event.ui.dto.PostEventRequest;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeleteEventServiceTest {

    private DeleteEventService deleteEventService;

    @Mock
    private ReviewEventRepository reviewEventRepository;
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ReviewEventLogRepository reviewEventLogRepository;

    @BeforeEach
    public void init() {
        deleteEventService = new DeleteEventService(reviewEventRepository,
            reviewRepository, userRepository, reviewEventLogRepository);
    }

    @Test
    public void deleteEventFailNotFoundReviewExceptionTest() {
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

        when(reviewRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NotFoundReviewException.class, () -> {
            deleteEventService.deleteEvent(postEventRequest);
        });
    }

    @Test
    public void deleteEventFailNotFoundEventExceptionTest() {
        String reviewId = "reviewId";
        Set<String> attachedPhotoIds = new HashSet<>();
        attachedPhotoIds.add("1");
        attachedPhotoIds.add("2");
        String content = " 컨텐츠";
        String userId = "userId";
        String placeId = "placeId";
        Set<Image> images = attachedPhotoIds.stream().map((o) -> {
            return new Image(o);
        }).collect(
            Collectors.toSet());
        Review review = new Review(reviewId, content, images, userId, placeId);
        PostEventRequest postEventRequest = new PostEventRequest("REVIEW", ReviewEventAction.ADD,
            reviewId,
            content, attachedPhotoIds, userId, placeId);

        when(reviewRepository.findById(any())).thenReturn(Optional.of(review));
        when(reviewEventRepository.findByReviewId(any())).thenReturn(Optional.empty());

        assertThrows(NotFoundEventException.class, () -> {
            deleteEventService.deleteEvent(postEventRequest);
        });
    }

    @Test
    public void deleteEventFailNotFoundUserExceptionTest() {
        String reviewId = "reviewId";
        String reviewEventId = "reviewEventId";
        Set<String> attachedPhotoIds = new HashSet<>();
        attachedPhotoIds.add("1");
        attachedPhotoIds.add("2");
        String content = " 컨텐츠";
        String userId = "userId";
        String placeId = "placeId";
        Set<Image> images = attachedPhotoIds.stream().map((o) -> {
            return new Image(o);
        }).collect(
            Collectors.toSet());
        Review review = new Review(reviewId, content, images, userId, placeId);
        ReviewEvent reviewEvent = new ReviewEvent(reviewEventId,reviewId,placeId,new ReviewScore(
            Score.ONE,Score.ONE,Score.ONE));
        PostEventRequest postEventRequest = new PostEventRequest("REVIEW", ReviewEventAction.ADD,
            reviewId,
            content, attachedPhotoIds, userId, placeId);

        when(reviewRepository.findById(any())).thenReturn(Optional.of(review));
        when(reviewEventRepository.findByReviewId(any())).thenReturn(Optional.of(reviewEvent));
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NotFoundUserException.class, () -> {
            deleteEventService.deleteEvent(postEventRequest);
        });
    }

    @Test
    public void deleteEventSuccessTest() {
        String reviewId = "reviewId";
        String reviewEventId = "reviewEventId";
        Set<String> attachedPhotoIds = new HashSet<>();
        attachedPhotoIds.add("1");
        attachedPhotoIds.add("2");
        String content = " 컨텐츠";
        String userId = "userId";
        String placeId = "placeId";
        Set<Image> images = attachedPhotoIds.stream().map((o) -> {
            return new Image(o);
        }).collect(
            Collectors.toSet());
        Review review = new Review(reviewId, content, images, userId, placeId);
        ReviewEvent reviewEvent = new ReviewEvent(reviewEventId,reviewId,placeId,new ReviewScore(
            Score.ONE,Score.ONE,Score.ONE));
        User user = new User(userId,new Score(3));
        PostEventRequest postEventRequest = new PostEventRequest("REVIEW", ReviewEventAction.ADD,
            reviewId,
            content, attachedPhotoIds, userId, placeId);

        when(reviewRepository.findById(any())).thenReturn(Optional.of(review));
        when(reviewEventRepository.findByReviewId(any())).thenReturn(Optional.of(reviewEvent));
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        deleteEventService.deleteEvent(postEventRequest);

        assertEquals(Score.ZERO,user.getScore());
    }
}