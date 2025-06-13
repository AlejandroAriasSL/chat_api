package com.chat.chat_api.message.dto;

import java.time.LocalDateTime;

public record MessageDTO(
    LocalDateTime timestamp, 
    String content, 
    Long senderId
){}
