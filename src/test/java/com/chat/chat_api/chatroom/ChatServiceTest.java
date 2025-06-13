package com.chat.chat_api.chatroom;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.chat.chat_api.chatroom.dto.ChatDTO;
import com.chat.chat_api.chatroom.dto.CreateChatRequestDTO;
import com.chat.chat_api.chatroom.exception.ChatNotFoundException;

@DisplayName("ChatService unit tests")
public class ChatServiceTest {
    
    private ChatroomRepository repository = mock(ChatroomRepository.class);
    private ChatService chatService = new ChatService(repository);

    @Test
    @DisplayName("ChatService correctly saves chat and returns DTO")
    void test_chatService_correctly_saves_chat(){

        String chatName = "Chat1";
        ChatDTO expectedResult = ChatDTO.toDTO(new Chatroom(chatName, 1L));

        CreateChatRequestDTO createChatRequest = new CreateChatRequestDTO(chatName);
        when(repository.save(any(Chatroom.class))).thenReturn(new Chatroom(chatName, 1L));

        ChatDTO actualResult = chatService.createOrUpdate(createChatRequest);
        assertThat(actualResult, is(equalTo(expectedResult)));

    }

    @Test
    @DisplayName("ChatService returns chat by id")
    void test_ChatService_returns_chat_by_id(){

        String chatName = "Chat1";
        CreateChatRequestDTO createChatRequestDTO = new CreateChatRequestDTO(chatName);
        Chatroom savedChat = new Chatroom(chatName, 1L);

        when(repository.save(any(Chatroom.class))).thenReturn(savedChat);
        when(repository.findById(savedChat.getId())).thenReturn(Optional.of(savedChat));

        chatService.createOrUpdate(createChatRequestDTO);
        assertThat(chatService.getById(savedChat.getId()), is(equalTo(savedChat)));

    }

    @Test
    @DisplayName("ChatService returns all the saved chats")
    void test_ChatService_returns_all_chats(){

        String chatName = "Chat1";
        Chatroom savedChat1 = new Chatroom(chatName, 1L);
        Chatroom savedChat2 = new Chatroom(chatName, 2L);

        when(repository.findAll()).thenReturn(List.of(savedChat1, savedChat2));

        List<ChatDTO> result = chatService.getAll();        
        assertThat(result, hasSize(2));
    }

    @Test
    @DisplayName("ChatService deletes chatById")
    void test_ChatService_deletes_chat_by_id(){

        String chatName = "Chat1";
        Chatroom savedChat = new Chatroom(chatName, 1L);

        when(repository.findById(savedChat.getId())).thenReturn(Optional.of(savedChat));
        when(repository.existsById(1L)).thenReturn(true);

        Chatroom queriedChat = chatService.getById(1L);
        assertThat(queriedChat, is(equalTo(savedChat)));

        chatService.deleteById(1L);
        reset(repository);

        assertThrows(ChatNotFoundException.class, () -> chatService.getById(1L));

    }
}
