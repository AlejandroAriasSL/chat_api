package com.chat.chat_api.chatroom;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.chat.chat_api.message.MessageDTO;

@Controller
public class ChatController {

    @MessageMapping("/chat")
    @SendTo("/topic/messages")

    public MessageDTO send(MessageDTO message) {
        return new MessageDTO(message.timestamp(), message.content(), message.senderUsername());
    }
}
