package com.example.WITHUS.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequestDto {

    private String currentPassword;

    private String newPassword;

    private String newPasswordCheck;


    public CharSequence getcurretPassword() {
        return currentPassword;
    }
}