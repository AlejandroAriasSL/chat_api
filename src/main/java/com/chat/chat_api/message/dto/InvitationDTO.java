package com.chat.chat_api.message.dto;

import java.time.LocalDateTime;

public record InvitationDTO(
    Long chatId,
    String senderUsername,
    String recieverUsername,
    LocalDateTime sentAt
) {}
