package com.example.WITHUS.Repository;



import com.example.WITHUS.Repository.ChatRepository;
import com.example.WITHUS.entity.Chat;
import com.example.WITHUS.entity.Croom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {
    List<Chat> findByCroomIdxOrderByCreatedAtAsc(Croom croom);

    Optional<Chat> findTop1ByCroomIdxOrderByCreatedAtDesc(Croom croom); // 🔥 요거 추가!
}