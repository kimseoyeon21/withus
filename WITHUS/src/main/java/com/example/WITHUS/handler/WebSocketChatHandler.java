package com.example.WITHUS.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashSet;
import java.util.Set;

@Component
public class WebSocketChatHandler extends TextWebSocketHandler {

    @Getter
    private static final Set<WebSocketSession> sessions = new HashSet<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        System.out.println("🔵 연결됨: " + session.getId());
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("💬 받은 메시지: " + message.getPayload());

        // 받은 메시지가 JSON이면 그대로 브로드캐스트
        for (WebSocketSession s : sessions) {
            if (s.isOpen()) {
                s.sendMessage(new TextMessage(message.getPayload()));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
        System.out.println("🔴 연결 끊김: " + session.getId());
    }
}
