package com.example.WITHUS.Repository;

import com.example.WITHUS.entity.Like;
import com.example.WITHUS.entity.LikeId;
import com.example.WITHUS.entity.User;
import com.example.WITHUS.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, LikeId> {
    // 유저와 게시글로 좋아요 여부 확인
    Optional<Like> findByUserAndCommunity(User user, Community community);

    // 특정 게시글의 좋아요 수 세기
    long countByCommunity(Community community);
}