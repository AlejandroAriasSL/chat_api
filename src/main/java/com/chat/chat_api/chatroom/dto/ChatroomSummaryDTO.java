package com.chat.chat_api.chatroom.dto;

import java.util.List;

import com.chat.chat_api.message.dto.MessageResponseDTO;

public record ChatroomSummaryDTO(
    Long roomId,
    String chatName,
    List<MessageResponseDTO> messages
){}
