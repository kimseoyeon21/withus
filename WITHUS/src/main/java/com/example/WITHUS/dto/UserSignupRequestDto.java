package com.example.WITHUS.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserSignupRequestDto {
    private String id;
    private String password;
    private String passwordCheck;
    private String name;
    private String email;
    private String birthday;
}
