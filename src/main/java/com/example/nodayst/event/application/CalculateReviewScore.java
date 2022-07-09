package com.example.nodayst.event.application;

import com.example.nodayst.common.model.Score;
import com.example.nodayst.common.model.ReviewScore;
import com.example.nodayst.event.infra.ReviewEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CalculateReviewScore {

    private final ReviewEventRepository reviewEventRepository;

    public ReviewScore calculateReviewScore(String placeId, long imagesSize, long textLength) {
        Score textScore = Score.ZERO;
        Score photoScore = Score.ZERO;
        Score firstReviewScore = Score.ZERO;
        if (!reviewEventRepository.existsByPlaceId(placeId)) {
            firstReviewScore = Score.ONE;
        }
        if (imagesSize > 0) {
            textScore = Score.ONE;
        }
        if (textLength > 0) {
            photoScore = Score.ONE;
        }
        return new ReviewScore(textScore, photoScore, firstReviewScore);
    }

    public ReviewScore calculateReviewScore(Score firstReviewScore, long imagesSize, long textLength) {
        Score textScore = Score.ZERO;
        Score photoScore = Score.ZERO;
        if (imagesSize > 0) {
            textScore = Score.ONE;
        }
        if (textLength > 0) {
            photoScore = Score.ONE;
        }
        return new ReviewScore(textScore, photoScore, firstReviewScore);
    }
}
