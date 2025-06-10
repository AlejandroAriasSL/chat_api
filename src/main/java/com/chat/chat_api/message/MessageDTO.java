package com.chat.chat_api.message;

import java.time.LocalDateTime;

public record MessageDTO(
    LocalDateTime timestamp, 
    String content, 
    Long senderId
){}
