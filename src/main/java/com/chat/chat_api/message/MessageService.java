package com.chat.chat_api.message;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.chat.chat_api.chatroom.ChatService;
import com.chat.chat_api.chatroom.Chatroom;
import com.chat.chat_api.message.dto.MessageDTO;
import com.chat.chat_api.message.dto.MessageResponseDTO;
import com.chat.chat_api.user.User;
import com.chat.chat_api.user.UserService;

import jakarta.transaction.Transactional;

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

    @Transactional
    public MessageResponseDTO createOrUpdate(Long chatId, MessageDTO messageDto, String username) {
        Chatroom chat = chatService.getById(chatId);
        User user = userService.findByUsername(username).orElseThrow();

        Message message = new Message(LocalDateTime.now(), messageDto.content(), user, chat);
        repository.save(message);

        return MessageResponseDTO.toDTO(message);
    }
}
