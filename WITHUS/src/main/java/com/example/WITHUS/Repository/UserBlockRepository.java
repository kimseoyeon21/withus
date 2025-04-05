package com.example.WITHUS.Repository;

import com.example.WITHUS.entity.Block;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserBlockRepository extends JpaRepository<Block, Long> {
    // 차단한 유저 목록 조회
    List<Block> findByBlockingUserId(String blockingUserId);

    // 특정 유저가 이미 차단한 유저가 있는지 확인
    boolean existsByBlockingUserIdAndBlockedUserId(String blockingUserId, String blockedUserId);

    // 차단 해제
    void deleteByBlockingUserIdAndBlockedUserId(String blockingUserId, String blockedUserId);
}