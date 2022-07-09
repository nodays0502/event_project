package com.example.nodayst.common.dto;

public class ResponseMessage {

    public static String SUCCESS_STATUS = "SUCCESS";
    public static String FAIL_STATUS = "FAIL";


    public static String SUCCESS_MESSAGE = "요청이 성공하였습니다.";
    public static String BAD_REQUEST_MESSAGE = "잘못된 입력입니다.";

    public static String ALREADY_REVIEW_HAS_EVENT_MESSAGE = "해당 리뷰에는 이미 이벤트 포인트가 존재합니다.";
    public static String NOT_FOUND_EVENT_MESSAGE = "해당 이벤트를 찾을 수 없습니다.";
    public static String NOT_FOUND_REVIEW_EVENT_ACTION_MESSAGE = "해당 리뷰 이벤트 관련 종류가 존재하지 않습니다.";
    public static String NOT_FOUND_PLACE_MESSAGE = "해당 장소를 찾을 수 없습니다.";
    public static String NOT_FOUND_REVIEW_MESSAGE = "해당 리뷰를 찾을 수 없습니다.";
    public static String NOT_FOUND_USER_MESSAGE = "해당 유저를 찾을 수 없습니다.";
}
