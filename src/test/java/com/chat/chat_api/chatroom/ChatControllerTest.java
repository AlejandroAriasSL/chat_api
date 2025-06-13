package com.chat.chat_api.chatroom;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import com.chat.chat_api.chatroom.dto.ChatDTO;
import com.chat.chat_api.chatroom.dto.CreateChatRequestDTO;

@DisplayName("ChatController unit tests")
public class ChatControllerTest {
    
    private ChatService chatService = mock(ChatService.class);
    private ChatRestController controller = new ChatRestController(chatService);

    @Test
    @DisplayName("ChatController creates chat")
    void test_ChatController_creates_chat(){

       String chatName = "Chat1";

       CreateChatRequestDTO createChatRequestDTO = new CreateChatRequestDTO(chatName);
       Chatroom chat = new Chatroom(chatName, 1L);
       ChatDTO chatDTO = ChatDTO.toDTO(chat);
       
       when(chatService.createOrUpdate(createChatRequestDTO)).thenReturn(chatDTO);
       ResponseEntity<ChatDTO> response = controller.createChat(createChatRequestDTO);

       assertThat(response.getStatusCode().is2xxSuccessful(), is(equalTo(true)));
       assertThat(response.getBody(), is(equalTo(chatDTO)));

    }
}
