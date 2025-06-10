package com.chat.chat_api.message;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    private final SimpMessagingTemplate messagingTemplate;

    public MessageController(
        SimpMessagingTemplate messagingTemplate
    ){
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat/{chatId}/send")
    public void sendMessage(@DestinationVariable Long chatId, MessageDTO messageDTO){
        messagingTemplate.convertAndSend("/topic/chat/" + chatId + "/messages", messageDTO);
    }
}
