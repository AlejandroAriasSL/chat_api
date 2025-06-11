package com.chat.chat_api.utils;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.chat.chat_api.chatroom.ChatService;
import com.chat.chat_api.chatroom.Chatroom;
import com.chat.chat_api.chatroom.ChatroomRepository;
import com.chat.chat_api.user.User;
import com.chat.chat_api.user.UserRepository;
import com.chat.chat_api.user.UserService;
import com.chat.chat_api.user.dto.CreateUserRequestDTO;

public class UserServiceTestContext {
    
    private UserRepository userRepository;
    private ChatroomRepository chatRepository;
    private ChatService chatService;
    private UserService userService;
    
    public UserServiceTestContext() {
        userRepository = mock(UserRepository.class);
        chatRepository = mock(ChatroomRepository.class);
        chatService = new ChatService(chatRepository);
        userService = new UserService(userRepository, chatService);
    }

    public void verifyAddChatToUserInteractions(User mockUser, Chatroom mockChat){
        verify(userRepository, times(1)).findById(mockUser.getId());
        verify(chatRepository, times(1)).findById(mockChat.getId());
        verify(userRepository, times(1)).save(mockUser);
    }

    public void mockAddChatToUserImplementation(User mockUser, Chatroom mockChat){
        when(userRepository.findById(mockUser.getId())).thenReturn(Optional.of(mockUser));
        when(chatRepository.findById(mockChat.getId())).thenReturn(Optional.of(mockChat));
        when(userRepository.save(mockUser)).thenReturn(mockUser);
    }

    public ChatroomRepository getChatRepository() {
        return chatRepository;
    }

    public ChatService getChatService() {
        return chatService;
    }


    public UserRepository getUserRepository() {
        return userRepository;
    }

    public UserService getUserService() {
        return userService;
    }

    public CreateUserRequestDTO createUserRequest(String username){
        return new CreateUserRequestDTO(username);
    }

    public User createMockUser(String username){
        return new User(username);
    }
    
    public User createMockUser(String username, Long id){
        return new User(username, id);
    }
}
