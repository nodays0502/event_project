package com.example.nodayst.event.ui.dto;


import com.example.nodayst.event.domain.ReviewEventAction;
import com.example.nodayst.event.domain.ReviewEventLog;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserScoreHistory {

    private ReviewEventAction action;
    private long gap;

    public static UserScoreHistory ReviewEventLogMapToUserScoreHistory(
        ReviewEventLog reviewEventLog) {
        return new UserScoreHistory(reviewEventLog.getAction(),
            reviewEventLog.getGap().getValue());
    }
}
