package com.chat.chat_api.invitation;

public record InvitationDTO(
    Long chatId,
    String senderUsername,
    String recieverUsername
) {}
