package com.chat.chat_api.chatroom.dto;

import java.util.List;

import com.chat.chat_api.message.MessageResponseDTO;

public record ChatroomSummaryDTO(
    Long id,
    String name,
    List<MessageResponseDTO> messages
){}
