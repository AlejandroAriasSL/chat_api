package com.chat.chat_api.user;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.chat.chat_api.chatroom.ChatService;
import com.chat.chat_api.chatroom.Chatroom;
import com.chat.chat_api.chatroom.ChatroomRepository;
import com.chat.chat_api.user.dto.CreateUserRequestDTO;
import com.chat.chat_api.user.dto.UserDTO;
import com.chat.chat_api.user.exception.UserNotFoundException;

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

    private void verifyAddChatToUserInteractions(User mockUser, Chatroom mockChat){
        verify(userRepository, times(1)).findById(mockUser.getId());
        verify(chatRepository, times(1)).findById(mockChat.getId());
        verify(userRepository, times(1)).save(mockUser);
    }

    private void mockAddChatToUserImplementation(User mockUser, Chatroom mockChat){
        when(userRepository.findById(mockUser.getId())).thenReturn(Optional.of(mockUser));
        when(chatRepository.findById(mockChat.getId())).thenReturn(Optional.of(mockChat));
        when(userRepository.save(mockUser)).thenReturn(mockUser);
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

    @Test
    @DisplayName("UserService retrieves all users")
    void test_retrieves_all_users(){

        User mockUser = new User(createUserRequest.username(), id);
        User mockUser2 = new User(createUserRequest.username(), 2L);

        when(userRepository.findAll()).thenReturn(List.of(mockUser, mockUser2));

        List<User> expectedUsers = List.of(mockUser, mockUser2);

        assertThat(userService.getAll(), is(equalTo(expectedUsers)));
        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("UserService deletes player when player found")
    void test_deletes_player_when_player_found(){

        when(userRepository.existsById(id)).thenReturn(true);

        userService.deleteById(id);

        verify(userRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("UserService throws exception when player not found before deleting")
    void test_throws_exception_if_doesnt_exist_before_deletion(){

        assertThrows(UserNotFoundException.class, () -> userService.deleteById(id));
    }

    @Test
    @DisplayName("UserService adds chat to user")
    void test_adds_chat_to_user(){

        Chatroom mockChat = new Chatroom("mockChat", id);
        User mockUser = new User(username, id);

        mockAddChatToUserImplementation(mockUser, mockChat);
        UserDTO userChats = userService.addChatToUser(id, 1L);

        assertThat(userChats.chatIds(), hasItem(mockChat.getId()));
        
        verifyAddChatToUserInteractions(mockUser, mockChat);
    }

    @Test
    @DisplayName("UserService doesnt duplicate chat if user already joined")
    void test_doesnt_add_chat_to_user_if_already_contains_it(){

        Chatroom mockChat = new Chatroom("mockChat", id);
        User mockUser = new User(username, id);

        mockUser.getChats().add(mockChat);
        assertThat(mockUser.getChats(), hasSize(1));

        mockAddChatToUserImplementation(mockUser, mockChat);
        UserDTO userChats = userService.addChatToUser(id, 1L);

        assertThat(userChats.chatIds(), hasItem(mockChat.getId()));
        assertThat(userChats.chatIds(), hasSize(1));

        verifyAddChatToUserInteractions(mockUser, mockChat);;
    }
}
