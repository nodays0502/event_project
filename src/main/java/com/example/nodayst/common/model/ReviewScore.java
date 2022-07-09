package com.example.nodayst.common.model;

import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewScore {

    @Embedded
    private Score textScore;
    @Embedded
    private Score photoScore;
    @Embedded
    private Score firstReviewScore;

    public ReviewScore(Score textScore, Score photoScore,
        Score firstReviewScore) {
        this.textScore = textScore;
        this.photoScore = photoScore;
        this.firstReviewScore = firstReviewScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReviewScore that = (ReviewScore) o;
        return Objects.equals(textScore, that.textScore) && Objects.equals(
            photoScore, that.photoScore) && Objects.equals(firstReviewScore,
            that.firstReviewScore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(textScore, photoScore, firstReviewScore);
    }

    public Score calculateScore() {
        return Score.add(textScore, photoScore, firstReviewScore);
    }
}
