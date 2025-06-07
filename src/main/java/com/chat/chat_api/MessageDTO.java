package com.chat.chat_api;

import java.time.LocalDateTime;

public record MessageDTO(
    LocalDateTime timestamp, 
    String content, 
    String senderUsername
){}
