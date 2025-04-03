package com.example.WITHUS.controller;

import com.example.WITHUS.Repository.UserRepository;
import com.example.WITHUS.dto.Signup;
import com.example.WITHUS.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.sql.Timestamp;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SignupController {
    private final UserRepository userRepository;


    @PostMapping("/signup")
    @Transactional
    public ResponseEntity<String> signup(@RequestBody Signup dto) {

        // 1. 필수 항목 체크
        if (dto.getUserId() == null || dto.getUserPassword() == null || dto.getUserPasswordCheck() == null ||
                dto.getUserName() == null || dto.getUserNick() == null ||
                dto.getUserEmail() == null || dto.getUserBirthdate() == null) {
            return ResponseEntity.badRequest().body("❌ 모든 항목을 입력해야 합니다.");
        }

        // 2. 비밀번호 일치 여부 확인
        if (!dto.getUserPassword().equals(dto.getUserPasswordCheck())) {
            return ResponseEntity.badRequest().body("❌ 비밀번호가 일치하지 않습니다.");
        }

        // 3. 엔티티 저장
        User user = User.builder()
                .userId(dto.getUserId())
                .userName(dto.getUserName())
                .userPassword(dto.getUserPassword())
                .userNick(dto.getUserNick())
                .userEmail(dto.getUserEmail())
                .userBirthdate(Date.valueOf(dto.getUserBirthdate()))
                .userRole("USER")
                .joinedAt(new Timestamp(System.currentTimeMillis()))
                .build();

        userRepository.save(user);

        return ResponseEntity.ok("✅ 회원가입 성공!");
    }
}

