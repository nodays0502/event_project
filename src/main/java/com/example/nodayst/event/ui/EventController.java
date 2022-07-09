package com.example.nodayst.event.ui;

import com.example.nodayst.common.dto.ResponseMessage;
import com.example.nodayst.common.dto.ResponseWithDataDto;
import com.example.nodayst.common.dto.ResponseWithOutDataDto;
import com.example.nodayst.common.exception.event.NotFoundReviewEventActionException;
import com.example.nodayst.event.application.AddEventService;
import com.example.nodayst.event.application.DeleteEventService;
import com.example.nodayst.event.application.GetEventScoreService;
import com.example.nodayst.event.application.GetUserScoreHistoryService;
import com.example.nodayst.event.application.ModifyEventService;
import com.example.nodayst.event.domain.ReviewEventAction;
import com.example.nodayst.event.ui.dto.GetUserScoreHistoryResponse;
import com.example.nodayst.event.ui.dto.PostEventRequest;
import com.example.nodayst.event.ui.dto.GetEventScoreResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/events")
public class EventController {

    private final AddEventService addEventService;
    private final DeleteEventService deleteEventService;
    private final ModifyEventService modifyEventService;
    private final GetEventScoreService getEventScoreService;
    private final GetUserScoreHistoryService getUserScoreHistoryService;


    @PostMapping("")
    public ResponseEntity postEvent(@Valid @RequestBody PostEventRequest postEventRequest) {

        if (postEventRequest.getAction() == ReviewEventAction.ADD) {
            addEventService.addEvent(postEventRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseWithOutDataDto(ResponseMessage.SUCCESS_STATUS,
                    ResponseMessage.SUCCESS_MESSAGE));

        } else if (postEventRequest.getAction() == ReviewEventAction.MOD) {
            modifyEventService.modifyEvent(postEventRequest);
            return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseWithOutDataDto(ResponseMessage.SUCCESS_STATUS,
                    ResponseMessage.SUCCESS_MESSAGE));

        } else if (postEventRequest.getAction() == ReviewEventAction.DELETE) {
            deleteEventService.deleteEvent(postEventRequest);
            return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseWithOutDataDto(ResponseMessage.SUCCESS_STATUS,
                    ResponseMessage.SUCCESS_MESSAGE));
        } else{
            throw new NotFoundReviewEventActionException();
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity getEventScore(@PathVariable String userId) {
        return ResponseEntity.status(HttpStatus.OK).body(
            new ResponseWithDataDto(ResponseMessage.SUCCESS_STATUS,
                ResponseMessage.SUCCESS_MESSAGE,
                new GetEventScoreResponse(getEventScoreService.getScore(userId))));
    }

    @GetMapping("/{userId}/score_history")
    public ResponseEntity getUserScoreHistory(@PathVariable String userId) {
        return ResponseEntity.status(HttpStatus.OK).body(
            new ResponseWithDataDto(ResponseMessage.SUCCESS_STATUS,
                ResponseMessage.SUCCESS_MESSAGE,
                new GetUserScoreHistoryResponse(
                    getUserScoreHistoryService.getUserScoreHistory(userId))));
    }
}
