package com.example.WITHUS.controller;

import com.example.WITHUS.entity.Community;
import com.example.WITHUS.entity.User;
import com.example.WITHUS.Repository.CommunityRepository;
import com.example.WITHUS.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/community")
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;

    // 게시글 업로드
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createPost(
            @RequestPart("title") String title,
            @RequestPart("content") String content,
            @RequestPart("userId") String userId,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) throws IOException {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("❌ 유저가 존재하지 않습니다."));

        String fileName = null;
        if (file != null && !file.isEmpty()) {
            fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            String savePath = "C:/community_uploads/" + fileName;
            File saveFile = new File(savePath);
            if (!saveFile.getParentFile().exists()) {
                saveFile.getParentFile().mkdirs();
            }
            file.transferTo(saveFile);
        }

        Community comm = new Community();
        comm.setCommTitle(title);
        comm.setCommContent(content);
        comm.setCommFile(fileName);
        comm.setCreatedAt(Instant.now());
        comm.setCommLikes(0);
        comm.setUser(user);

        communityRepository.save(comm);

        return ResponseEntity.ok("✅ 게시글 등록 성공!");
    }

    // 전체 게시글 조회 (피드용)
    @GetMapping
    public ResponseEntity<List<Community>> getAllPosts() {
        List<Community> posts = communityRepository.findAll();
        return ResponseEntity.ok(posts);
    }

    // 이미지 제공 API
    @GetMapping("/images/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) throws MalformedURLException {
        File file = new File("C:/community_uploads/" + filename);
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }
        Resource resource = new UrlResource(file.toURI());
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

    // 좋아요 추가/삭제
    @PostMapping("/{communityId}/like")
    public ResponseEntity<?> toggleLike(@PathVariable Integer communityId,
                                        @RequestHeader("Authorization") String token) {
        // JWT 토큰에서 사용자 ID 추출 (예시로 간단히 처리)
        String userId = "extracted_from_token"; // JWT에서 실제 사용자 ID를 추출해야 합니다.
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("❌ 유저가 존재하지 않습니다."));

        // 게시글 조회
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new RuntimeException("❌ 게시글을 찾을 수 없습니다."));

        // 좋아요 수 증가 또는 감소
        if (community.getCommLikes() > 0) {
            // 좋아요가 이미 눌렸으면, 좋아요 취소 (수 감소)
            community.setCommLikes(community.getCommLikes() - 1);
        } else {
            // 좋아요가 안 눌렸으면, 좋아요 추가 (수 증가)
            community.setCommLikes(community.getCommLikes() + 1);
        }

        // 게시글 저장
        communityRepository.save(community);

        // 좋아요 상태에 따른 메시지 반환
        return ResponseEntity.ok("✅ 좋아요 상태가 변경되었습니다.");
    }
}