package com.example.nodayst.common.exception;

import com.example.nodayst.common.dto.ResponseMessage;
import com.example.nodayst.common.dto.ResponseWithOutDataDto;
import com.example.nodayst.common.exception.event.AlreadyReviewHasEvent;
import com.example.nodayst.common.exception.event.NotFoundEventException;
import com.example.nodayst.common.exception.event.NotFoundReviewEventActionException;
import com.example.nodayst.common.exception.place.NotFoundPlaceException;
import com.example.nodayst.common.exception.review.NotFoundReviewException;
import com.example.nodayst.common.exception.user.NotFoundUserException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
        WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ResponseWithOutDataDto(ResponseMessage.FAIL_STATUS,
                ResponseMessage.BAD_REQUEST_MESSAGE));
    }

    @ExceptionHandler(AlreadyReviewHasEvent.class)
    public ResponseEntity handleAlreadyReviewHasEvent(
        AlreadyReviewHasEvent ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(new ResponseWithOutDataDto(ResponseMessage.FAIL_STATUS,
                ResponseMessage.ALREADY_REVIEW_HAS_EVENT_MESSAGE));
    }

    @ExceptionHandler(NotFoundEventException.class)
    public ResponseEntity handleNotFoundEventException(
        NotFoundEventException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(new ResponseWithOutDataDto(ResponseMessage.FAIL_STATUS,
                ResponseMessage.NOT_FOUND_EVENT_MESSAGE));
    }

    @ExceptionHandler(NotFoundReviewEventActionException.class)
    public ResponseEntity handleNotFoundReviewEventActionException(
        NotFoundReviewEventActionException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(new ResponseWithOutDataDto(ResponseMessage.FAIL_STATUS,
                ResponseMessage.NOT_FOUND_REVIEW_EVENT_ACTION_MESSAGE));
    }

    @ExceptionHandler(NotFoundPlaceException.class)
    public ResponseEntity handleNotFoundPlaceException(
        NotFoundPlaceException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(new ResponseWithOutDataDto(ResponseMessage.FAIL_STATUS,
                ResponseMessage.NOT_FOUND_PLACE_MESSAGE));
    }

    @ExceptionHandler(NotFoundReviewException.class)
    public ResponseEntity handleNotFoundReviewException(
        NotFoundReviewException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(new ResponseWithOutDataDto(ResponseMessage.FAIL_STATUS,
                ResponseMessage.NOT_FOUND_REVIEW_MESSAGE));
    }

    @ExceptionHandler(NotFoundUserException.class)
    public ResponseEntity handleNotFoundUserException(
        NotFoundUserException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(new ResponseWithOutDataDto(ResponseMessage.FAIL_STATUS,
                ResponseMessage.NOT_FOUND_USER_MESSAGE));
    }
}
