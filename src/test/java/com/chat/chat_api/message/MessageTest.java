package com.chat.chat_api.message;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.chat.chat_api.chatroom.Chatroom;
import com.chat.chat_api.user.User;

@DisplayName("Message unit tests")
public class MessageTest {
    
    @Test
    @DisplayName("Message entity initializes with correct attributes")
    void test_message_initializes_with_correct_attributes(){

        String content = "Hola";
        LocalDateTime timestamp = LocalDateTime.now();
        User sender = new User("User1");
        Chatroom chat = new Chatroom("Chat1");

        Message message = new Message(timestamp, content, sender, chat);

        assertThat(message.getContent(), is(equalTo(content)));
        assertThat(message.getTimestamp(), is(equalTo(timestamp)));
        assertThat(message.getSender(), is(equalTo(sender)));
        assertThat(message.getChat(), is(equalTo(chat)));
    }
}
