package com.example.WITHUS.Repository;

import com.example.WITHUS.entity.Community;
import com.example.WITHUS.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityRepository extends JpaRepository<Community, Integer> {
    List<Community> findByUserId(User user);
}