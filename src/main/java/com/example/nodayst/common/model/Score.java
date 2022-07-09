package com.example.nodayst.common.model;

import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Score {
    @Transient
    public static final Score ONE = new Score(1);
    @Transient
    public static final Score ZERO = new Score(0);

    private long value;

    public Score(long value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Score score = (Score) o;
        return value == score.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public static Score add(Score... scores){
        int value = 0;
        for(Score score : scores){
            value += score.getValue();
        }
        return new Score(value);
    }

    public Score minus(Score score){
        return new Score(this.value - score.getValue());
    }
    public static Score changeSign(Score score){
        return new Score(-score.value);
    }
}
