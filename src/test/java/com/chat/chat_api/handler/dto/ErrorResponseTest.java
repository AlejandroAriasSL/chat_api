package com.chat.chat_api.handler.dto;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ErrorResponse unit test")
public class ErrorResponseTest {
    
    @Test
    @DisplayName("Error response initializes with correct attributes")
    void test_errorResponse_initializes_with_correct_attributes(){

        int status = 404;
        String error = "Not found";
        String message = "The request could not be processed";

        ErrorResponse errorResponse = new ErrorResponse(status, error, message);
        
        assertThat(errorResponse.status(), is(equalTo(status)));
        assertThat(errorResponse.error(), is(equalTo(error)));
        assertThat(errorResponse.message(), is(equalTo(message)));

    }
}
