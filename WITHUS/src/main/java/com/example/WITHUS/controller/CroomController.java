package com.example.WITHUS.controller;

import com.example.WITHUS.Repository.ChatRepository;
import com.example.WITHUS.Repository.CroomRepository;
import com.example.WITHUS.Repository.UserRepository;
import com.example.WITHUS.dto.ChatMessageDto;
import com.example.WITHUS.entity.Chat;
import com.example.WITHUS.entity.Croom;
import com.example.WITHUS.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/croom")
@RequiredArgsConstructor

public class CroomController {
        private final ChatRepository chatRepository;
        private final CroomRepository croomRepository;
        private final UserRepository userRepository;

        // ✅ 채팅 조회
        @GetMapping("/{croomIdx}/chat")
        public ResponseEntity<List<Chat>> getChats(@PathVariable Integer croomIdx) {
            Croom croom = croomRepository.findById(croomIdx).orElse(null);
            if (croom == null) {
                return ResponseEntity.badRequest().build();
            }
            List<Chat> chats = chatRepository.findByCroomIdxOrderByCreatedAtAsc(croom);
            return ResponseEntity.ok(chats);
        }

        // ✅ 채팅 전송
        @PostMapping("/{croomIdx}/chat")
        public ResponseEntity<String> sendChat(@PathVariable Integer croomIdx, @RequestBody ChatMessageDto msg) {
            Croom croom = croomRepository.findById(croomIdx).orElse(null);
            User user = userRepository.findById(msg.getSenderId()).orElse(null);

            if (croom == null || user == null) {
                return ResponseEntity.status(403).body("❌ 채팅방 또는 유저 정보 없음");
            }

            Chat chat = new Chat();
            chat.setCroomIdx(croom);
            chat.setChatter(user);
            chat.setChatContent(msg.getContent());
            chat.setCreatedAt(Instant.now());

            chatRepository.save(chat);
            return ResponseEntity.ok("✅ 채팅 전송 완료");
        }

        // ✅ 채팅방 목록 + 마지막 메시지
        @GetMapping("/list")
        public List<Map<String, Object>> getChatRoomsWithLastMessages() {
            List<Croom> rooms = croomRepository.findAll();
            List<Map<String, Object>> result = new ArrayList<>();

            for (Croom room : rooms) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", room.getId());
                map.put("name", room.getCroomTitle());
                map.put("image", "https://source.unsplash.com/random/200x200?sig=" + room.getId());

                // 마지막 메시지
                Optional<Chat> optionalChat = chatRepository.findTop1ByCroomIdxOrderByCreatedAtDesc(room);
                Chat lastChat = optionalChat.orElse(null);
                if (lastChat != null) {
                    map.put("lastMessage", lastChat.getChatContent());
                } else {
                    map.put("lastMessage", "대화를 시작해보세요!");
                }

                result.add(map);
            }

            return result;
        }
        @PostMapping("/create")
        public ResponseEntity<Map<String, Object>> createRoom(@RequestBody Map<String, String> request) {
            String myUserId = request.get("userId");

            if (myUserId == null || myUserId.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "❌ 유저 ID 없음"));
            }

            Optional<User> myUserOpt = userRepository.findById(myUserId);
            if (myUserOpt.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "❌ 유저 정보 없음"));
            }

            User myUser = myUserOpt.get();

            // ✅ 기존에 생성된 방이 있는지 확인 (방 제목이 유저 닉네임 기준으로 만들어졌으므로 중복 체크 가능)
            Optional<Croom> existingRoom = croomRepository.findAll().stream()
                    .filter(r -> r.getUser().getUserId().equals(myUserId) && r.getCroomTitle().contains(myUser.getUserNick()))
                    .findFirst();

            if (existingRoom.isPresent()) {
                return ResponseEntity.ok(Map.of("id", existingRoom.get().getId(), "title", existingRoom.get().getCroomTitle()));
            }

            // ✅ 새 채팅방 생성
            Croom newRoom = new Croom();
            newRoom.setUser(myUser);
            newRoom.setCroomTitle(myUser.getUserNick() + "의 채팅방");
            newRoom.setCreatedAt(Instant.now());
            newRoom.setCroomStatus("active");
            newRoom.setCroomLimit(2);

            croomRepository.save(newRoom);

            return ResponseEntity.ok(Map.of("id", newRoom.getId(), "title", newRoom.getCroomTitle()));
        }




    }

