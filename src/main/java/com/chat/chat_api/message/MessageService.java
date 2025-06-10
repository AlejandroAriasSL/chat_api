package com.chat.chat_api.message;

import org.springframework.stereotype.Service;

import com.chat.chat_api.chatroom.ChatService;
import com.chat.chat_api.chatroom.Chatroom;
import com.chat.chat_api.user.User;
import com.chat.chat_api.user.UserService;

@Service
public class MessageService {

    private final MessageRepository repository;
    private final ChatService chatService;
    private final UserService userService;

    public MessageService(
        MessageRepository repository,
        ChatService chatService,
        UserService userService
    ){
        this.repository = repository;
        this.chatService = chatService;
        this.userService = userService;
    }

    public MessageResponseDTO createOrUpdate(Long chatId, MessageDTO messageDto){
        Chatroom chat = chatService.getById(chatId);
        
        User user = userService.getById(messageDto.senderId());

        Message message = new Message(messageDto.timestamp(), messageDto.content(), user, chat);

        repository.save(message);

        return new MessageResponseDTO(
            message.getId(),
            message.getContent(),
            message.getTimestamp(),
            message.getSender().getId(),
            message.getSender().getUsername()
        );
    }
}
