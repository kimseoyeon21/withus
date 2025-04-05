package com.example.WITHUS.Repository;



import com.example.WITHUS.entity.Chat;
import com.example.WITHUS.entity.Croom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Integer> {
    List<Chat> findByCroomIdxOrderByCreatedAtAsc(Croom croom);
}