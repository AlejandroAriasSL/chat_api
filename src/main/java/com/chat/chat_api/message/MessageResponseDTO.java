package com.chat.chat_api.message;

import java.time.LocalDateTime;

public record MessageResponseDTO(
    Long id, 
    String content, 
    LocalDateTime timestamp,
    Long senderId,
    String senderUsername
){}
