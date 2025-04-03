package com.example.WITHUS.controller;

import com.example.WITHUS.Repository.FollowRepository;
import com.example.WITHUS.dto.FollowDto;
import com.example.WITHUS.entity.Follow;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RequiredArgsConstructor
public class FollowController {

    private final FollowRepository followRepository;

    // 1️⃣ 팔로우 추가
    @PostMapping
    public ResponseEntity<String> follow(@RequestBody FollowDto dto) {
        if (!followRepository.existsByFollowerAndFollowee(dto.getFollower(), dto.getFollowee())) {
            Follow follow = Follow.builder()
                    .follower(dto.getFollower())
                    .followee(dto.getFollowee())
                    .followedAt(new Timestamp(System.currentTimeMillis()).toString())
                    .build();
            followRepository.save(follow);
            return ResponseEntity.ok("팔로우 성공");
        } else {
            return ResponseEntity.badRequest().body("이미 팔로우 중");
        }
    }

    // 2️⃣ 팔로우 취소
    @DeleteMapping
    public ResponseEntity<String> unfollow(@RequestBody FollowDto dto) {
        if (followRepository.existsByFollowerAndFollowee(dto.getFollower(), dto.getFollowee())) {
            followRepository.deleteByFollowerAndFollowee(dto.getFollower(), dto.getFollowee());
            return ResponseEntity.ok("언팔로우 성공");
        } else {
            return ResponseEntity.badRequest().body("팔로우 상태가 아닙니다");
        }
    }

    // 3️⃣ 팔로우 여부 확인
    @GetMapping("/check")
    public ResponseEntity<Boolean> isFollowing(@RequestParam String follower, @RequestParam String followee) {
        boolean exists = followRepository.existsByFollowerAndFollowee(follower, followee);
        return ResponseEntity.ok(exists);
    }

    // 4️⃣ 팔로워 목록 조회 (나를 팔로우하는 사람들)
    @GetMapping("/followers/{userId}")
    public ResponseEntity<List<Follow>> getFollowers(@PathVariable String userId) {
        List<Follow> followers = followRepository.findByFollowee(userId);
        return ResponseEntity.ok(followers);
    }

    // 5️⃣ 팔로잉 목록 조회 (내가 팔로우한 사람들)
    @GetMapping("/followings/{userId}")
    public ResponseEntity<List<Follow>> getFollowings(@PathVariable String userId) {
        List<Follow> followings = followRepository.findByFollower(userId);
        return ResponseEntity.ok(followings);
    }
}
