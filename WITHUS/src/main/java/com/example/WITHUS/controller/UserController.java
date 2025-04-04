package com.example.WITHUS.controller;

import com.example.WITHUS.Repository.UserRepository;
import com.example.WITHUS.config.JwtUtil;
import com.example.WITHUS.dto.UserProfileUpdateDto;
import com.example.WITHUS.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public UserController(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    // 프로필 편집 (이름, 소개 변경)
    @PutMapping("/update")
    public ResponseEntity<?> updateProfile(@RequestBody UserProfileUpdateDto request,
                                           @RequestHeader("Authorization") String token) {
        // JWT 토큰에서 사용자 정보 추출
        String userId = jwtUtil.extractUserId(token);

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
        }

        // 사용자 조회
        Optional<User> userOpt = userRepository.findById(userId);

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
        }

        User user = userOpt.get();

        // 프로필 사진, 이름과 소개 변경
        user.setProfileImg(request.getProfileImg()); // 프로필 사진 변경
        user.setUserNick(request.getUserNick());  // 이름 변경
        user.setProfileInfo(request.getProfileInfo());    // 소개 변경

        // DB에 저장
        userRepository.save(user);

        return ResponseEntity.ok("프로필이 업데이트되었습니다.");
    }
}