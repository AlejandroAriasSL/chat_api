package com.chat.chat_api.message;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.chat.chat_api.chatroom.ChatService;
import com.chat.chat_api.chatroom.Chatroom;
import com.chat.chat_api.message.dto.MessageDTO;
import com.chat.chat_api.message.dto.MessageResponseDTO;
import com.chat.chat_api.user.User;
import com.chat.chat_api.user.UserService;

@ExtendWith(MockitoExtension.class)
@DisplayName("MessageService unit tests")
public class MessageServiceTest {

    @Mock
    private MessageRepository repository;
    
    @Mock
    private ChatService chatService;
    
    @Mock
    private UserService userService;

    @InjectMocks
    private MessageService messageService;

    
    @Test
    @DisplayName("MessageService should return MessageResponseDTO when saving entity")
    void test_messageService_should_return_response_dto_after_save(){

        Long chatId = 1L;
        Long senderId = 2L;

        MessageDTO messageDto = new MessageDTO(LocalDateTime.now(), "Hola", senderId);
        
        Chatroom chat = new Chatroom("Chat1", chatId);
        User user = new User("Usuario1", senderId);

        when(chatService.getById(chatId)).thenReturn(chat);
        when(userService.getById(senderId)).thenReturn(user);

        Message message = new Message(messageDto.timestamp(), messageDto.content(), user, chat);
        MessageResponseDTO expectedResult = MessageResponseDTO.toDTO(message);

        assertThat(messageService.createOrUpdate(chatId, messageDto), is(equalTo(expectedResult)));
        
        verify(chatService, times(1)).getById(chatId);
        verify(userService, times(1)).getById(senderId);
    }
}
