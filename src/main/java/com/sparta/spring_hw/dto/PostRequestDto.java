package com.sparta.spring_hw.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PostRequestDto {
    private String title;
    private String username;
    private String password;
    private String contents;
}
