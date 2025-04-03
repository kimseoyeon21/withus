package com.example.WITHUS.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Signup {
    private String userId;
    private String userPassword;
    private String userPasswordCheck;
    private String userName;
    private String userNick;
    private String userEmail;
    private LocalDate userBirthdate;



}
