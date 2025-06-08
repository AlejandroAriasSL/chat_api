package com.chat.chat_api.chatroom;

import java.util.List;

import com.chat.chat_api.user.User;

public record ChatDTO(Long id, String name, List<Long> usersId) {

    public static ChatDTO toDTO(Chatroom chat){
        List<Long> userIds = chat.getChatUsers()
                                 .stream()
                                 .map(User::getId)
                                 .toList();
                                 
        return new ChatDTO(chat.getId(), chat.getName(), userIds);
    }
}
