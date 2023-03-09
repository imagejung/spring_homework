package com.sparta.spring_hw.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageResponseDto {
    private String message;
    private int status;

    public MessageResponseDto(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
