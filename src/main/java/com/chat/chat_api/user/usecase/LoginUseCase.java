package com.chat.chat_api.user.usecase;

import java.util.Base64;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import com.chat.chat_api.annotation.UseCaseType;
import com.chat.chat_api.security.JwtUtils;
import com.chat.chat_api.user.User;
import com.chat.chat_api.user.UserService;
import com.chat.chat_api.user.UserUseCase;
import com.chat.chat_api.user.dto.LoginRequestDTO;
import com.chat.chat_api.user.dto.LoginResponseDTO;

@Component
@UseCaseType(LoginRequestDTO.class)
public class LoginUseCase implements UserUseCase<LoginRequestDTO, LoginResponseDTO> {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public LoginUseCase(UserService userService, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    public LoginResponseDTO execute(LoginRequestDTO request) {
        
        byte[] decodedBytes = Base64.getDecoder().decode(request.password());
        String passwordDecoded = new String(decodedBytes);
        
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(request.username(), passwordDecoded);
        authenticationManager.authenticate(authToken);

        User user = userService.findByUsername(request.username())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));


        String token = jwtUtils.generateJwt(user);
        
        return new LoginResponseDTO("Login exitoso", token);
    }
}
