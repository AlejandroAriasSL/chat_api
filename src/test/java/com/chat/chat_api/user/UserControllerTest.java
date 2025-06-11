package com.chat.chat_api.user;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import com.chat.chat_api.chatroom.Chatroom;

import com.chat.chat_api.user.dto.CreateUserRequestDTO;
import com.chat.chat_api.user.dto.UserDTO;
import com.chat.chat_api.utils.UserTestContext;

@DisplayName("UserController unit tests")
public class UserControllerTest {

    private UserTestContext context;
    private UserController controller;
    private User mockUser;

    @BeforeEach
    void setUp(){
        context = new UserTestContext();
        context.setMocked(mock(UserService.class));
        controller = new UserController(context.getUserService());
        mockUser = context.createMockUser("Usuario1", 1L);
    }
    
    @Test
    @DisplayName("UserController creates user")
    void test_controller_returns_userDTO(){

        CreateUserRequestDTO createUserRequest = context.createUserRequest("Usuario1");

        when(context.getUserService().createOrUpdate(createUserRequest)).thenReturn(mockUser);

        ResponseEntity<UserDTO> response = controller.createUser(createUserRequest);
        assertThat(response.getStatusCode(), is(equalTo(HttpStatusCode.valueOf(201))));
    }

    @Test
    @DisplayName("UserController creates chat with user")
    void test_controller_creates_chat_with_user(){
   
        String chatName = "mockChat";
        Chatroom mockChat = new Chatroom(chatName, 1L);

        mockUser.getChats().add(mockChat);

        when(context.getUserService().createChatWithUser(mockUser.getId(), chatName)).thenReturn(UserDTO.toDto(mockUser));
        
        ResponseEntity<UserDTO> response = controller.createChatWithUser(mockUser.getId(), mockChat.getName());

        assertThat(response.getStatusCode(), is(equalTo(HttpStatusCode.valueOf(200))));
        assertThat(response.getBody().userId(), is(equalTo(mockUser.getId())));
        assertThat(response.getBody().chatIds(), hasItem(mockChat.getId()));
    }
}
