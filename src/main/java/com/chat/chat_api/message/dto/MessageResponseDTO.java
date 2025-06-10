package com.chat.chat_api.message.dto;

import java.time.LocalDateTime;

import com.chat.chat_api.message.Message;

public record MessageResponseDTO(
    Long id, 
    String content, 
    LocalDateTime timestamp,
    Long senderId,
    String senderUsername
){
    public static MessageResponseDTO toDTO(Message message){
        return new MessageResponseDTO(
            message.getId(),
            message.getContent(),
            message.getTimestamp(),
            message.getSender().getId(),
            message.getSender().getUsername() 
        );  
    }
}
