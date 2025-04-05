package com.example.WITHUS.controller;


import com.example.WITHUS.Repository.UserRepository;
import com.example.WITHUS.dto.UserChangePasswordRequestDto;
import com.example.WITHUS.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserChangePasswordController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody UserChangePasswordRequestDto dto) {

        // 👉 로그인된 유저 정보는 이미 확보된 상태라고 가정
        // 실제 구현 시 세션, 토큰, 필터 등에서 유저 ID 가져오기
        Long loggedInUserId = 1L; // 예시: 로그인된 사용자 ID (임시 하드코딩)

        User user = userRepository.findById(String.valueOf(loggedInUserId))
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 1. 현재 비밀번호 일치 여부 확인
        if (!passwordEncoder.matches(dto.getcurretPassword(), user.getUserPassword())) {
            return ResponseEntity.badRequest().body("현재 비밀번호가 일치하지 않습니다.");
        }

        // 2. 새 비밀번호와 확인 값 비교
        if (!dto.getNewPassword().equals(dto.getNewPasswordCheck())) {
            return ResponseEntity.badRequest().body("새 비밀번호와 확인 값이 일치하지 않습니다.");
        }

        // 3. 비밀번호 저장
        user.setUserPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);

        return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
    }
}