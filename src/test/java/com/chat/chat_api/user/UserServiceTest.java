package com.chat.chat_api.user;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.chat.chat_api.chatroom.ChatService;
import com.chat.chat_api.chatroom.ChatroomRepository;
import com.chat.chat_api.user.dto.CreateUserRequestDTO;

@DisplayName("UserService unit tests")
public class UserServiceTest {

    private String username = "Usuario1";

    private UserRepository userRepository = mock(UserRepository.class);
    private ChatroomRepository chatRepository = mock(ChatroomRepository.class);
    private ChatService chatService = new ChatService(chatRepository);
    private CreateUserRequestDTO createUserRequest = new CreateUserRequestDTO(username);


    @Test
    @DisplayName("UserService succesfully creates user")
    void test_user_is_created(){
       
       UserService userService = new UserService(userRepository, chatService);
       User mockUser = new User(createUserRequest.username());

       when(userRepository.save(any(User.class))).thenReturn(mockUser);

       User savedUser = userService.createOrUpdate(createUserRequest);
       
       assertThat(savedUser, is(equalTo(mockUser)));
       verify(userRepository, times(1)).save(any(User.class));
    }
}
