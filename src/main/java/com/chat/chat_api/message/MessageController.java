package com.chat.chat_api.message;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.chat.chat_api.message.dto.MessageDTO;
import com.chat.chat_api.message.dto.MessageResponseDTO;

@Controller
public class MessageController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;

    public MessageController(
        SimpMessagingTemplate messagingTemplate,
        MessageService messageService
    ){
        this.messagingTemplate = messagingTemplate;
        this.messageService = messageService;
    }

    @MessageMapping("/chat/{chatId}/send")
    public void sendMessage(@DestinationVariable Long chatId,
                            MessageDTO messageDTO,
                            SimpMessageHeaderAccessor headerAccessor) {

        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username == null) {
            throw new RuntimeException("Usuario no autenticado");
        }

        MessageResponseDTO messageResponseDTO = messageService.createOrUpdate(chatId, messageDTO, username);

        messagingTemplate.convertAndSend("/topic/chat/" + chatId + "/messages", messageResponseDTO);
    }
}
