package com.chat.chat_api;

import java.time.LocalDateTime;

public record Message(
    LocalDateTime timestamp, 
    String content, 
    String senderUsername
){}
