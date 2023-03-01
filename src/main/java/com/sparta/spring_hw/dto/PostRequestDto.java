package com.sparta.spring_hw.dto;

import lombok.Getter;

@Getter
public class PostRequestDto {
    private String title;
    private String username;
    private String pw;
    private String contents;
}
