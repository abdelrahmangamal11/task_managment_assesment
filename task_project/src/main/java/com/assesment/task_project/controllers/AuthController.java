package com.assesment.task_project.controllers;

import com.assesment.task_project.domain.Dto.ApiErrorResponse;
import com.assesment.task_project.domain.Dto.AuthRequest;
import com.assesment.task_project.domain.Dto.AuthResponse;
import com.assesment.task_project.domain.Dto.UserDto;
import com.assesment.task_project.domain.entities.User;
import com.assesment.task_project.mappers.impl.UserMappersImpl;
import com.assesment.task_project.security.TokenBlacklistService;
import com.assesment.task_project.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserMappersImpl userMappers;
    private final TokenBlacklistService tokenBlacklistService;

    @GetMapping
    public List<User> users(){
        return  authService.findusers();
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserDto userDto ) {
        User user = userMappers.fromDtoToUser(userDto);

        User registeredUser = authService.register(user);

        UserDto registeredUserDto = userMappers.fromUserToDto(registeredUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUserDto);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest.LoginRequest loginRequest) {

        try {
            // Authenticate user
            UserDetails userDetails = authService.authenticate(
                    loginRequest.email(),
                    loginRequest.password()
            );

            // Generate token
            String token = authService.generateToken(userDetails);

            AuthResponse authResponse=AuthResponse.builder().token(token).expireIn(86400).build();
            return   ResponseEntity.ok(authResponse);

        }catch (BadCredentialsException ex) {
            // Return 400 with JSON error
            ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Incorrect email or password")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String token =extractToken(request);
        if (token != null) {
            tokenBlacklistService.blacklistToken(token);
        }
        return ResponseEntity.ok().build();
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}


