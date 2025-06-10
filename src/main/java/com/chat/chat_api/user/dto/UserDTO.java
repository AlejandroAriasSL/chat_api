package com.chat.chat_api.user.dto;

import java.util.List;

import com.chat.chat_api.chatroom.Chatroom;
import com.chat.chat_api.user.User;

public record UserDTO(Long userId, String username, List<Long> chatIds) {

    public static UserDTO toDto(User user){
        List<Long> ids = user.getChats()
                             .stream()
                             .map(Chatroom::getId)
                             .toList();

        return new UserDTO(user.getId(),user.getUsername(), ids);
    }
}

