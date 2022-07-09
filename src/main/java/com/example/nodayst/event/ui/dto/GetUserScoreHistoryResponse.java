package com.example.nodayst.event.ui.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetUserScoreHistoryResponse {
    List<UserScoreHistory> UserScoreHistories;
}
