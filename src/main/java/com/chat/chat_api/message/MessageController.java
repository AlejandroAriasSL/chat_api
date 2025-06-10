package com.chat.chat_api.message;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.chat.chat_api.message.dto.MessageDTO;

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
    public void sendMessage(@DestinationVariable Long chatId, MessageDTO messageDTO){
        messageService.createOrUpdate(chatId, messageDTO);
        messagingTemplate.convertAndSend("/topic/chat/" + chatId + "/messages", messageDTO);
    }
}
