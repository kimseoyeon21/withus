package com.example.WITHUS.Repository;

import com.example.WITHUS.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsByFollowerAndFollowee(String follower, String followee);
    void deleteByFollowerAndFollowee(String follower, String followee);
    long countByFollowee(String followee);
    List<Follow> findByFollowee(String followee);  // 팔로워 리스트
    List<Follow> findByFollower(String follower);  // 팔로잉 리스트
}
