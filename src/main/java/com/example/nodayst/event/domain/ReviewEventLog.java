package com.example.nodayst.event.domain;

import com.example.nodayst.common.model.Score;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewEventLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REVIEW_EVENT_LOG_ID")
    private Long id;

    @Column(name = "USER_ID")
    private String userId;

    @Enumerated(EnumType.STRING)
    private ReviewEventAction action;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "value", column = @Column(name = "GAP"))
    })
    private Score gap;

    public ReviewEventLog(String userId, ReviewEventAction action,
        Score gap) {
        this.userId = userId;
        this.action = action;
        this.gap = gap;
    }

    public ReviewEventLog(long id, String userId,
        ReviewEventAction action, Score gap) {
        this.id = id;
        this.userId = userId;
        this.action = action;
        this.gap = gap;
    }
}
