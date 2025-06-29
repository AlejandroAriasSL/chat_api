package com.chat.chat_api.user.usecase;

import java.util.Base64;
import java.util.Base64.Decoder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.chat.chat_api.role.RoleService;
import com.chat.chat_api.user.User;
import com.chat.chat_api.user.UserService;
import com.chat.chat_api.user.dto.CreateUserRequestDTO;
import com.chat.chat_api.user.dto.UserDTO;

@Component
public class RegisterUseCase {

    private final UserService userService;
    private final RoleService roleService;

    public RegisterUseCase(UserService userService, RoleService roleService){
        this.userService = userService;
        this.roleService = roleService;
    }

    public UserDTO execute(CreateUserRequestDTO request){

        Decoder decoder = Base64.getDecoder();
        byte[] decodedBytes = decoder.decode(request.password());
        String passwordDecoded = new String(decodedBytes);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String passwordEnconded = encoder.encode(passwordDecoded);

        User newUser = new User(request.username(), passwordEnconded);
        newUser.setRole(roleService.getDefaultRole());

        User savedUser = userService.createOrUpdate(newUser);
        return UserDTO.toDto(savedUser);
    }

}
