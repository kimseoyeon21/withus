package com.example.WITHUS.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data

public class ChatMessageDto {
    private String senderId;
    private String content;
}
