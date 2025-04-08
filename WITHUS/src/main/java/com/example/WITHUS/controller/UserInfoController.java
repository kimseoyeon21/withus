package com.example.WITHUS.controller;

import com.example.WITHUS.Repository.UserRepository;
import com.example.WITHUS.config.JwtUtil;
import com.example.WITHUS.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.util.Optional;

@CrossOrigin(origins ="*")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserInfoController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    // 사용자 정보 조회 API
    @PostMapping("/me")
    public ResponseEntity<?> getMyInfo(@RequestHeader(value = "Authorization", required = false) String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("❌ Authorization header가 없습니다.");
        }

        String userId = jwtUtil.extractUserId(token.substring(7));
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("❌ 유효하지 않은 토큰입니다.");
        }

        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ 사용자를 찾을 수 없습니다.");
        }

        User user = userOpt.get();


        return ResponseEntity.ok(user);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserProfile(@PathVariable String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("❌ 유저를 찾을 수 없습니다."));
        return ResponseEntity.ok(user);
    }

}