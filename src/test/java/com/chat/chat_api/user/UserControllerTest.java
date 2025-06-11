package com.chat.chat_api.user;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import com.chat.chat_api.user.dto.CreateUserRequestDTO;
import com.chat.chat_api.user.dto.UserDTO;
import com.chat.chat_api.utils.UserTestContext;

@DisplayName("UserController unit tests")
public class UserControllerTest {

    private UserTestContext context;
    private UserController controller;

    @BeforeEach
    void setUp(){
        context = new UserTestContext();
        context.setMocked(mock(UserService.class));
        controller = new UserController(context.getUserService());
    }
    
    @Test
    @DisplayName("UserController creates user")
    void test_controller_returns_userDTO(){

        String username = "Usuario1";
        Long id = 1L;

        CreateUserRequestDTO createUserRequest = context.createUserRequest(username);
        User mockUser = context.createMockUser(username, id);

        when(context.getUserService().createOrUpdate(createUserRequest)).thenReturn(mockUser);

        ResponseEntity<UserDTO> response = controller.createUser(createUserRequest);
        assertThat(response.getStatusCode(), is(equalTo(HttpStatusCode.valueOf(201))));
    }
}
