package com.example.WITHUS.Repository;

import com.example.WITHUS.entity.Comment;
import com.example.WITHUS.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    // 특정 게시글에 대한 댓글을 조회하는 메서드
    List<Comment> findByCommIdx(Community community);
}