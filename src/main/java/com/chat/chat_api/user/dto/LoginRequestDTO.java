package com.chat.chat_api.user.dto;

public record LoginRequestDTO(String username, String password) implements UserRecord {

}
