package com.chat.chat_api.user.dto;

public record LoginResponseDTO(String message, String token) implements UserRecord {

}
