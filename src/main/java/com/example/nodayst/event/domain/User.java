package com.example.nodayst.event.domain;


import com.example.nodayst.common.model.Score;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "USER_ID")
    private String id;


    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "value", column = @Column(name = "TOTAL_SCORE"))
    })
    private Score score;

    public void addScore(Score score) {
        this.score = Score.add(this.score, score);
    }

}

