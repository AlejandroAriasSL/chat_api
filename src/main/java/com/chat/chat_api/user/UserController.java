package com.chat.chat_api.user;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.chat_api.user.dto.CreateUserRequestDTO;
import com.chat.chat_api.user.dto.LoginRequestDTO;
import com.chat.chat_api.user.dto.LoginResponseDTO;
import com.chat.chat_api.user.dto.UserChatsDTO;
import com.chat.chat_api.user.dto.UserDTO;

@RestController
@RequestMapping("${api.baseurl}/users")
public class UserController {

    private final UserService userService;
    private final UserFacade userFacade; 

    public UserController(final UserService userService, UserFacade userFacade){
        this.userService = userService;
        this.userFacade = userFacade;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<UserDTO> createUser(@RequestBody CreateUserRequestDTO request){
        UserDTO response = userFacade.execute(request);
        URI location = URI.create("api/v1/users" + "/" + response.userId());
        return ResponseEntity.created(location).body(response);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request){
        return ResponseEntity.ok(userFacade.execute(request));
    }

    @PostMapping("/{userId}/chats/create") 
    public ResponseEntity<UserDTO> createChatWithUser(@PathVariable Long userId, @RequestBody String chatName){
        return ResponseEntity.ok(userService.createChatWithUser(userId, chatName));
    }

    @PostMapping("/{userId}/chats")
    public ResponseEntity<UserDTO> addChatToUser(@PathVariable Long userId, @RequestBody Long chatId){
        return ResponseEntity.ok(userService.addChatToUser(userId, chatId));
    }

    @GetMapping("/{userId}/chats")
    public ResponseEntity<UserChatsDTO> getUserChats(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getUserChats(userId));
    }
}
