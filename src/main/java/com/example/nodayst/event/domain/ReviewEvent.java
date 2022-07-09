package com.example.nodayst.event.domain;

import com.example.nodayst.common.model.Score;
import com.example.nodayst.common.model.ReviewScore;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewEvent {

    @Id
    @Column(name = "REVIEW_EVENT_ID")
    private String id;

    private String reviewId;

    private String placeId;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "textScore.value", column = @Column(name = "TEXT_SCORE")),
        @AttributeOverride(name = "photoScore.value", column = @Column(name = "PHOTO_SCORE")),
        @AttributeOverride(name = "firstReviewScore.value", column = @Column(name = "FIRST_REVIEW_SCORE"))
    })
    private ReviewScore reviewScore;

    public ReviewEvent(String id, String reviewId, String placeId, ReviewScore point) {
        this.id = id;
        this.reviewId = reviewId;
        this.placeId = placeId;
        this.reviewScore = point;
    }

    public Score calTotalPoint() {
        return reviewScore.calculateScore();
    }

    public void changeScore(ReviewScore scoreSum) {
        this.reviewScore = scoreSum;
    }
}
