package com.example.WITHUS.Repository;

import com.example.WITHUS.entity.Croom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CroomRepository extends JpaRepository<Croom, Long> {
    List<Croom> findByUser_UserId(String userId);  // User 객체의 userId 필드를 기준으로 찾음
}