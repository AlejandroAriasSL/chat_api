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

    @Test
    @DisplayName("User has correct attributes after initialization")
    void test_user_has_correct_attributes_after_initialization(){
        
        String name = "Usuario1";
        Long id = 1L;

        User user = new User(name, id);

        assertThat(user.getUsername(), is(equalTo(name)));
        assertThat(user.getId(), is(equalTo(id)));
    }
}
