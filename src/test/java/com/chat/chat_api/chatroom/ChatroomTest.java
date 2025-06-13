package com.chat.chat_api.chatroom;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Chatroom unit tests")
public class ChatroomTest {
    
    @Test
    @DisplayName("Chatroom constructor with no parameters")
    void test_chatroom_constructor_with_no_parameters(){

        Chatroom chat = new Chatroom();

        assertNull(chat.getId());
        assertNull(chat.getName());
        assertThat(chat.getMessages().isEmpty(), is(equalTo(true)));
        assertThat(chat.getChatUsers().isEmpty(), is(equalTo(true)));
    }
}
