package com.chat.chat_api;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat")
    @SendTo("/topic/messages")

    public MessageDTO send(MessageDTO message) {
        return new MessageDTO(message.timestamp(), message.content(), message.senderUsername());
    }
}
