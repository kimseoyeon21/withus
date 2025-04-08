package com.example.WITHUS.Repository;


import com.example.WITHUS.entity.Croom;
import com.example.WITHUS.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CroomRepository extends JpaRepository<Croom, Integer> {
    Optional<Croom> findByCroomTitle(String croomTitle);  // 방 제목으로 중복 체크용
    List<Croom> findByUser(User user); // 유저가 만든 채팅방 목록
}