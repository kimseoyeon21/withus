package com.example.WITHUS.controller;

import com.example.WITHUS.Repository.UserRepository;
import com.example.WITHUS.dto.UserProfileUpdateDto;
import com.example.WITHUS.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserProfileUpdateController {

    private final UserRepository userRepository;

    public UserProfileUpdateController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 프로필 편집 (이름, 소개 변경)
    @PutMapping("/update")
    public ResponseEntity<?> updateProfile(@RequestBody UserProfileUpdateDto request) {
        // 이미 프론트에서 받아온 userId를 그대로 사용
        String userId = request.getUserId(); // 프로필 변경에서 받은 userId

        // 유저 정보 직접 업데이트 (조회는 불필요)
        User user = userRepository.findById(userId).get(); // 유저가 존재한다고 가정

        // 프로필 사진, 이름과 소개 변경
        user.setProfileImg(request.getProfileImg()); // 프로필 사진 변경
        user.setUserNick(request.getUserNick());  // 이름 변경
        user.setProfileInfo(request.getProfileInfo());    // 소개 변경

        // DB에 저장
        userRepository.save(user);

        return ResponseEntity.ok("프로필이 업데이트되었습니다.");
    }
}