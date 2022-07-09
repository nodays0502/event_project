package com.example.nodayst.event.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.example.nodayst.common.model.ReviewScore;
import com.example.nodayst.common.model.Score;
import java.util.UUID;
import org.junit.jupiter.api.Test;


class ReviewEventTest {
    @Test
    public void createTest(){
        String id = UUID.randomUUID().toString();
        String reviewId = UUID.randomUUID().toString();
        String placeId = UUID.randomUUID().toString();
        ReviewScore reviewScore = new ReviewScore(Score.ONE, Score.ONE, Score.ONE);

        ReviewEvent reviewEvent = new ReviewEvent(id, reviewId, placeId, reviewScore);

        assertEquals(new Score(3),reviewEvent.calTotalPoint());
    }
    @Test
    public void changeScoreTest(){
        String id = UUID.randomUUID().toString();
        String reviewId = UUID.randomUUID().toString();
        String placeId = UUID.randomUUID().toString();
        ReviewScore reviewScore = new ReviewScore(Score.ONE, Score.ONE, Score.ONE);
        ReviewEvent reviewEvent = new ReviewEvent(id, reviewId, placeId, reviewScore);
        ReviewScore newReviewScore = new ReviewScore(Score.ZERO, Score.ONE, Score.ONE);

        reviewEvent.changeScore(newReviewScore);

        assertEquals(new Score(2),reviewEvent.calTotalPoint());
    }
}