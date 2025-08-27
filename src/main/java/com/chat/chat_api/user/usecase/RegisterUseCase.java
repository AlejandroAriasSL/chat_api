package com.chat.chat_api.user.usecase;

import java.util.Base64;
import java.util.Base64.Decoder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.chat.chat_api.annotation.UseCaseType;
import com.chat.chat_api.role.RoleService;
import com.chat.chat_api.user.User;
import com.chat.chat_api.user.UserService;
import com.chat.chat_api.user.UserUseCase;
import com.chat.chat_api.user.dto.CreateUserRequestDTO;
import com.chat.chat_api.user.dto.UserDTO;

@Component
@UseCaseType(CreateUserRequestDTO.class)
public class RegisterUseCase implements UserUseCase<CreateUserRequestDTO, UserDTO> {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public RegisterUseCase(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO execute(CreateUserRequestDTO request){

        Decoder decoder = Base64.getDecoder();
        byte[] decodedBytes = decoder.decode(request.password());
        String passwordDecoded = new String(decodedBytes);

        String passwordEnconded = passwordEncoder.encode(passwordDecoded);

        User newUser = new User(request.username(), passwordEnconded);
        newUser.setRole(roleService.getDefaultRole());

        User savedUser = userService.createOrUpdate(newUser);
        return UserDTO.toDto(savedUser);
    }

}
