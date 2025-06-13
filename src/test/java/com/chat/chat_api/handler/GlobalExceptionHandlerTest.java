package com.chat.chat_api.handler;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.chat.chat_api.handler.dto.ErrorResponse;
import com.chat.chat_api.user.exception.UserNotFoundException;

@DisplayName("GlobalExceptionHandler unit tests")
public class GlobalExceptionHandlerTest {
    
    @Test
    @DisplayName("GlobalExceptionHanlder returns error response when UserNotFoundException is thrown")
    void test_returns_errorResponse_when_UserNotFoundException(){

        GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

        UserNotFoundException userNotFoundException = new UserNotFoundException(1L);

        ErrorResponse expectedErrorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), userNotFoundException.getMessage()); 
        
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleUserNotFound(userNotFoundException);

        assertThat(response.getStatusCode().is4xxClientError(), is(equalTo(true)));
        assertThat(response.getBody(), is(equalTo(expectedErrorResponse)));
    }
}
