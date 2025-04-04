package com.example.WITHUS.controller;

import com.example.WITHUS.Repository.UserRepository;
import com.example.WITHUS.dto.DeleteAccountRequestDto;
import com.example.WITHUS.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/delete")
@RequiredArgsConstructor
public class DeleteController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteAccount(@PathVariable String userId,
                                           @RequestBody DeleteAccountRequestDto dto) {
        // 1. 비밀번호와 확인값 일치 확인
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            return ResponseEntity.badRequest().body("비밀번호가 일치하지 않습니다.");
        }

        // 2. 유저 존재 여부 확인
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return ResponseEntity.badRequest().body("존재하지 않는 사용자입니다.");
        }

        // 3. 비밀번호 비교
        if (!passwordEncoder.matches(dto.getPassword(), user.getUserPassword())) {
            return ResponseEntity.badRequest().body("비밀번호가 올바르지 않습니다.");
        }

        // 4. 삭제
        userRepository.delete(user);
        return ResponseEntity.ok("계정이 성공적으로 삭제되었습니다.");
    }
}
