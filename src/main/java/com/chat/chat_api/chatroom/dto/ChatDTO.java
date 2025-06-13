package com.chat.chat_api.chatroom.dto;

import java.util.List;

import com.chat.chat_api.chatroom.Chatroom;
import com.chat.chat_api.user.User;

public record ChatDTO(Long roomId, String chatName, List<Long> usersId) {

    public static ChatDTO toDTO(Chatroom chat){
        List<Long> userIds = chat.getChatUsers()
                                 .stream()
                                 .map(User::getId)
                                 .toList();
                                 
        return new ChatDTO(chat.getId(), chat.getName(), userIds);
    }

    public static Chatroom toEntity(ChatDTO chatDTO){
        return new Chatroom(chatDTO.chatName(), chatDTO.roomId());
    }
}
