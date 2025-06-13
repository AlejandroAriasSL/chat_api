package com.chat.chat_api.message;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.chat.chat_api.message.dto.MessageDTO;

@ExtendWith(MockitoExtension.class)
@DisplayName("MessageController unit tests")
public class MessageControllerTest {
    
    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @Mock
    private MessageService messageService;

    @InjectMocks
    MessageController messageController;

    @Test
    @DisplayName("MessageController should persist and broadcasts message")
    void test_messageController_persists_and_broadcasts_messages(){
        
        Long chatId = 1L;
        Long senderId = 2L;

        MessageDTO messageDTO = new MessageDTO(LocalDateTime.now(), "Hola", senderId);

        messageController.sendMessage(chatId, messageDTO);

        String mockUri = "/topic/chat/" + chatId + "/messages";

        verify(messageService, times(1)).createOrUpdate(chatId, messageDTO);
        verify(messagingTemplate, times(1)).convertAndSend(mockUri, messageDTO);
    }
}
