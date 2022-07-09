package com.example.nodayst.common.dto;

import lombok.Data;

@Data
public class ResponseWithDataDto<T> extends ResponseWithOutDataDto {

    private T data;

    public ResponseWithDataDto(String status, String message, T data) {
        super(status, message);
        this.data = data;
    }
}
