package com.chat.chat_api.user;

import java.util.List;

import com.chat.chat_api.chatroom.ChatroomSummaryDTO;

public record UserChatsDTO(
    Long userId,
    String username,
    List<ChatroomSummaryDTO> chatrooms
){
    public static UserChatsDTO convert(User user, List<ChatroomSummaryDTO> chatSummary){
        return new UserChatsDTO(user.getId(), user.getUsername(), chatSummary);
    }
}
