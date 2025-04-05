package com.example.WITHUS.controller;

import com.example.WITHUS.entity.Comment;
import com.example.WITHUS.entity.Community;
import com.example.WITHUS.entity.User;
import com.example.WITHUS.Repository.CommentRepository;
import com.example.WITHUS.Repository.CommunityRepository;
import com.example.WITHUS.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/community")
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CommunityRepository communityRepository;

    // 댓글 추가
    @PostMapping("/{communityId}/comments")
    public ResponseEntity<?> addComment(@PathVariable Integer communityId,
                                        @RequestBody String content,
                                        @RequestHeader("Authorization") String token) {
        // 토큰에서 사용자 정보 추출 (이 부분은 JWT 처리 방식에 따라 다르게 구현해야 함)
        String userId = "extracted_from_token"; // 실제로 JWT 토큰을 파싱하여 사용자 ID를 추출합니다.
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("❌ 유저가 존재하지 않습니다."));

        // 게시글 조회
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new RuntimeException("❌ 게시글을 찾을 수 없습니다."));

        // 댓글 생성
        Comment comment = new Comment();
        comment.setCmtContent(content);
        comment.setCommIdx(community); // 해당 게시글에 댓글을 연결
        comment.setUser(user); // 댓글 작성자 설정

        // 댓글 저장
        commentRepository.save(comment);

        return ResponseEntity.ok("✅ 댓글이 추가되었습니다.");
    }

    // 댓글 조회 (특정 게시글에 대한 모든 댓글 조회)
    @GetMapping("/{communityId}/comments")
    public ResponseEntity<List<Comment>> getComments(@PathVariable Integer communityId) {
        // 게시글 조회
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new RuntimeException("❌ 게시글을 찾을 수 없습니다."));

        // 해당 게시글의 댓글 조회
        List<Comment> comments = commentRepository.findByCommIdx(community);

        return ResponseEntity.ok(comments);
    }
}