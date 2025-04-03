package com.example.WITHUS.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {
    private String userId;
    private String userPassword;
}
