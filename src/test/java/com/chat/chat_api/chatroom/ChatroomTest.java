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

    @Test
    @DisplayName("Chatroom has correct attributes after initialization")
    void test_chat_has_correct_attributes_after_initialization(){

        String name = "Chat1";
        Long id = 1L;

        Chatroom chat = new Chatroom(name, id);

        assertThat(chat.getId(), is(equalTo(id)));
        assertThat(chat.getName(), is(equalTo(name)));
    }

    @Test
    @DisplayName("Chatroom can change name after initialization")
    void tets_can_change_name_after_initialization(){

        String initialName = "Chat1";
        String modifiedName = "ChatOriginal";

        Chatroom chat =  new Chatroom(initialName);
        assertThat(chat.getName(), is(equalTo(initialName)));

        chat.setName(modifiedName);
        assertThat(chat.getName(), is(equalTo(modifiedName)));
    }
}
