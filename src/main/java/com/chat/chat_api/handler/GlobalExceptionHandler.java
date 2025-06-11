package com.chat.chat_api.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.chat.chat_api.chatroom.exception.ChatNotFoundException;
import com.chat.chat_api.handler.dto.ErrorResponse;
import com.chat.chat_api.user.exception.UserNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler({UserNotFoundException.class, ChatNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleUserNotFound(RuntimeException exception){
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            HttpStatus.NOT_FOUND.getReasonPhrase(),
            exception.getMessage()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
