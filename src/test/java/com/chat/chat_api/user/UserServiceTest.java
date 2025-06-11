package com.chat.chat_api.user;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.chat.chat_api.chatroom.Chatroom;
import com.chat.chat_api.chatroom.ChatroomRepository;
import com.chat.chat_api.chatroom.dto.ChatroomSummaryDTO;
import com.chat.chat_api.message.Message;
import com.chat.chat_api.user.dto.CreateUserRequestDTO;
import com.chat.chat_api.user.dto.UserChatsDTO;
import com.chat.chat_api.user.dto.UserDTO;
import com.chat.chat_api.user.exception.UserNotFoundException;
import com.chat.chat_api.utils.UserTestContext;

@DisplayName("UserService unit tests")
public class UserServiceTest {

    private String username = "Usuario1";
    private final Long id = 1L;

    private User mockUser;
    private UserTestContext context;
    private CreateUserRequestDTO createUserRequest;

    @BeforeEach
    void setUp(){
        context = new UserTestContext();
        createUserRequest = new CreateUserRequestDTO(username);
        mockUser = context.createMockUser(username, id);
    }

    private UserService getUserService(){ return context.getUserService(); }

    private UserRepository getUserRepository(){ return context.getUserRepository(); }

    private ChatroomRepository getChatRepository(){ return context.getChatRepository(); }

    @Test
    @DisplayName("UserService succesfully creates user")
    void test_user_is_created(){

       when(getUserRepository().save(any(User.class))).thenReturn(mockUser);

       User savedUser = getUserService().createOrUpdate(createUserRequest);
       
       assertThat(savedUser, is(equalTo(mockUser)));
       verify(getUserRepository(), times(1)).save(any(User.class));
    }


    @Test
    @DisplayName("UserService returns user by id when found")
    void test_user_found_by_id(){

        when(getUserRepository().findById(mockUser.getId())).thenReturn(Optional.of(mockUser));
        
        User foundUser = getUserService().getById(id);

        assertThat(foundUser, is(equalTo(mockUser)));
        verify(getUserRepository(), times(1)).findById(id);
    }

    @Test
    @DisplayName("UserService retrieves all users")
    void test_retrieves_all_users(){

        User mockUser2 = context.createMockUser(username, 2L);

        when(getUserRepository().findAll()).thenReturn(List.of(mockUser, mockUser2));

        List<User> expectedUsers = List.of(mockUser, mockUser2);

        assertThat(getUserService().getAll(), is(equalTo(expectedUsers)));
        verify(getUserRepository(), times(1)).findAll();
    }

    @Test
    @DisplayName("UserService deletes player when player found")
    void test_deletes_player_when_player_found(){

        when(getUserRepository().existsById(id)).thenReturn(true);

        getUserService().deleteById(id);

        verify(getUserRepository(), times(1)).deleteById(id);
    }

    @Test
    @DisplayName("UserService throws exception when player not found before deleting")
    void test_throws_exception_if_doesnt_exist_before_deletion(){

        assertThrows(UserNotFoundException.class, () -> context.getUserService().deleteById(id));
    }

    @Test
    @DisplayName("UserService adds chat to user")
    void test_adds_chat_to_user(){

        Chatroom mockChat = new Chatroom("mockChat", id);

        context.mockAddChatToUserImplementation(mockUser, mockChat);
        UserDTO userChats = getUserService().addChatToUser(id, 1L);

        assertThat(userChats.chatIds(), hasItem(mockChat.getId()));

        context.verifyAddChatToUserInteractions(mockUser, mockChat);
    }

    @Test
    @DisplayName("UserService doesnt duplicate chat if user already joined")
    void test_doesnt_add_chat_to_user_if_already_contains_it(){

        Chatroom mockChat = new Chatroom("mockChat", id);
        User mockUser = context.createMockUser(username, id);

        mockUser.getChats().add(mockChat);
        assertThat(mockUser.getChats(), hasSize(1));

        context.mockAddChatToUserImplementation(mockUser, mockChat);
        UserDTO userChats = getUserService().addChatToUser(id, 1L);

        assertThat(userChats.chatIds(), hasItem(mockChat.getId()));
        assertThat(userChats.chatIds(), hasSize(1));

        context.verifyAddChatToUserInteractions(mockUser, mockChat);;
    }

    @Test
    @DisplayName("UserService creates chat and assigns it to user")
    void test_creates_chat_and_assigns_to_user(){

        Chatroom mockChat = new Chatroom("mockChat", id);
        User mockUser = context.createMockUser(username, id);

        context.mockAddChatToUserImplementation(mockUser, mockChat);
        when(getChatRepository().save(any(Chatroom.class))).thenReturn(mockChat);

        UserDTO userChats = getUserService().createChatWithUser(mockUser.getId(), mockChat.getName());

        assertThat(userChats.chatIds(), hasItem(mockChat.getId()));

        context.verifyAddChatToUserInteractions(mockUser, mockChat);
        verify(getChatRepository(), times(1)).save(any(Chatroom.class));

    }

    @Test
    @DisplayName("UserService retrieves all chats from user")
    void test_returns_all_chats_from_user(){

        Long userId = mockUser.getId();

        List<Chatroom> mockUserChats = mockUser.getChats();

        Chatroom mockChat = new Chatroom("mockChat", id);
        
        List<Message> mockUserMessages = mockChat.getMessages();

        mockUserChats.add(mockChat);

        Message mockMessage1 = new Message(LocalDateTime.now(), "Hola", mockUser, mockChat);
        Message mockMessage2 = new Message(LocalDateTime.now(), "¿Qué tal?", mockUser, mockChat);

        mockUserMessages.add(mockMessage1);
        mockUserMessages.add(mockMessage2);

        when(getUserRepository().findById(id)).thenReturn(Optional.of(mockUser));

        UserChatsDTO userChats = getUserService().getUserChats(mockUser.getId());
        ChatroomSummaryDTO chatSummary = userChats.chatrooms().getFirst();

        assertThat(userChats.userId(), is(equalTo(userId)));
        assertThat(userChats.username(), is(equalTo(mockUser.getUsername())));
        assertThat(userChats.chatrooms(), hasSize(1));

        assertThat(chatSummary.messages(), hasSize(2));
    }
}
