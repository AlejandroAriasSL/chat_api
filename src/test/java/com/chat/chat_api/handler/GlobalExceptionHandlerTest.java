package com.chat.chat_api.handler;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.chat.chat_api.chatroom.exception.ChatNotFoundException;
import com.chat.chat_api.handler.dto.ErrorResponse;
import com.chat.chat_api.user.exception.UserNotFoundException;

@DisplayName("GlobalExceptionHandler unit tests")
public class GlobalExceptionHandlerTest {
    
    @Test
    @DisplayName("GlobalExceptionHandler returns error response when UserNotFoundException is thrown")
    void test_returns_errorResponse_when_UserNotFoundException(){

        GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

        UserNotFoundException userNotFoundException = new UserNotFoundException(1L);

        ErrorResponse expectedErrorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), userNotFoundException.getMessage()); 
        
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleUserNotFound(userNotFoundException);

        assertThat(response.getStatusCode().is4xxClientError(), is(equalTo(true)));
        assertThat(response.getBody(), is(equalTo(expectedErrorResponse)));
    }

    @Test
    @DisplayName("GlobalExceptionHandler returns error response when ChatNotfoundException is thrown")
    void test_returns_errorResponse_when_ChatNotFoundException(){

        GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

        ChatNotFoundException chatNotFoundException = new ChatNotFoundException(1L);

        ErrorResponse expectedErrorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), chatNotFoundException.getMessage());

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleUserNotFound(chatNotFoundException);

        assertThat(response.getStatusCode().is4xxClientError(), is(equalTo(true)));
        assertThat(response.getBody(), is(equalTo(expectedErrorResponse)));
    }
}
