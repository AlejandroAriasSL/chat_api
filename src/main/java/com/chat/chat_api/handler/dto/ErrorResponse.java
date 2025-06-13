package com.chat.chat_api.handler.dto;

public record ErrorResponse(int status, String error, String message) {
    
}
