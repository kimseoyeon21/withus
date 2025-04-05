package com.example.WITHUS.controller;

import com.example.WITHUS.Repository.ChatRepository;
import com.example.WITHUS.Repository.CroomRepository;
import com.example.WITHUS.Repository.UserRepository;
import com.example.WITHUS.dto.ChatMessageDto;
import com.example.WITHUS.dto.CroomDto;
import com.example.WITHUS.entity.Chat;
import com.example.WITHUS.entity.Croom;
import com.example.WITHUS.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/croom")
@RequiredArgsConstructor
public class CroomController {

    private final CroomRepository croomRepository;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    // 채팅방 생성
    @PostMapping("/create")
    public ResponseEntity<String> createCroom(@RequestBody CroomDto dto) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow();

        Croom croom = new Croom();
        croom.setCroomTitle(dto.getTitle());
        croom.setUser(user);
        croom.setCroomLimit(dto.getLimit());
        croom.setCroomStatus(dto.getStatus());
        croom.setCreatedAt(Instant.now());
        croom.setCroomType(dto.getType());

        croomRepository.save(croom);
        return ResponseEntity.ok("채팅방 생성 완료");
    }

    // 채팅방 내 채팅 메시지 조회
    @GetMapping("/{croomIdx}/chat")
    public ResponseEntity<List<Chat>> getChats(@PathVariable Integer croomIdx) {
        Croom croom = croomRepository.findById(Long.valueOf(croomIdx)).orElseThrow();
        return ResponseEntity.ok(chatRepository.findByCroomIdxOrderByCreatedAtAsc(croom));
    }

    // 채팅 메시지 전송
    @PostMapping("/{croomIdx}/chat")
    public ResponseEntity<String> sendChat(@PathVariable Integer croomIdx, @RequestBody ChatMessageDto msg) {
        Croom croom = croomRepository.findById(Long.valueOf(croomIdx)).orElseThrow();
        User user = userRepository.findById(msg.getSenderId()).orElseThrow();

        Chat chat = new Chat();
        chat.setCroomIdx(croom);
        chat.setChatter(user);
        chat.setChatContent(msg.getContent());
        chat.setCreatedAt(Instant.now());

        chatRepository.save(chat);
        return ResponseEntity.ok("채팅 전송 완료");
    }
}
