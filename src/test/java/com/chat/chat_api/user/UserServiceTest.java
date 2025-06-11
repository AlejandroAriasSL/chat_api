package com.chat.chat_api.user;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.chat.chat_api.chatroom.ChatService;
import com.chat.chat_api.chatroom.ChatroomRepository;
import com.chat.chat_api.user.dto.CreateUserRequestDTO;

@DisplayName("UserService unit tests")
public class UserServiceTest {

    private String username = "Usuario1";
    private final Long id = 1L;

    private UserRepository userRepository;
    private ChatroomRepository chatRepository;
    private ChatService chatService;
    private CreateUserRequestDTO createUserRequest;
    private UserService userService;


    @BeforeEach
    void setUp(){
        userRepository = mock(UserRepository.class);
        chatRepository = mock(ChatroomRepository.class);
        chatService =  new ChatService(chatRepository);
        createUserRequest = new CreateUserRequestDTO(username);
        userService = new UserService(userRepository, chatService);
    }

    @Test
    @DisplayName("UserService succesfully creates user")
    void test_user_is_created(){
       
       User mockUser = new User(createUserRequest.username());

       when(userRepository.save(any(User.class))).thenReturn(mockUser);

       User savedUser = userService.createOrUpdate(createUserRequest);
       
       assertThat(savedUser, is(equalTo(mockUser)));
       verify(userRepository, times(1)).save(any(User.class));
    }


    @Test
    @DisplayName("UserService returns user by id when found")
    void test_user_found_by_id(){

        User mockUser = new User(createUserRequest.username(), id);

        when(userRepository.findById(mockUser.getId())).thenReturn(Optional.of(mockUser));
        
        User foundUser = userService.getById(id);

        assertThat(foundUser, is(equalTo(mockUser)));
        verify(userRepository, times(1)).findById(id);
    }
}
