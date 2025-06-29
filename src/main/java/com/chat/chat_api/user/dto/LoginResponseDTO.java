package com.chat.chat_api.user.dto;

public record LoginResponseDTO(String message, String token, String role) implements UserRecord {

}
