package com.chat.chat_api.user;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("User entity unit tests")
public class UserTest {
    
    @Test
    @DisplayName("User constructor with no parameters")
    void test_user_constructor_without_parameters(){

        User user = new User();

        assertNull(user.getId());
        assertNull(user.getUsername());
        assertThat(user.getChats().isEmpty(), is(equalTo(true)));
    }
}
