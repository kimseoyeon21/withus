package com.example.WITHUS.controller;

import com.example.WITHUS.entity.User;
import com.example.WITHUS.security.UserPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    // JWT 인증된 사용자만 접근 가능
    @GetMapping("/me")
    public String getMyInfo(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userPrincipal.getUser();
        return "안녕하세요, " + user.getUserNick() + "님! (ID: " + user.getUserId() + ")";
    }
}