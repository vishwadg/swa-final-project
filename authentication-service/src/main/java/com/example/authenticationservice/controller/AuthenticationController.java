package com.example.authenticationservice.controller;


import com.example.authenticationservice.entities.DTOs.TokenResponse;
import com.example.commonsmodule.DTOs.UserDTO;
import com.example.authenticationservice.entities.DTOs.UserLoginDTO;
import com.example.authenticationservice.services.AuthenticationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping
    public TokenResponse login(@RequestBody UserLoginDTO payload) throws JsonProcessingException {
        return authenticationService.login(payload);
    }

    @PostMapping("/register")
    public UserDTO register(@RequestBody UserDTO payload){
        return authenticationService.register(payload);
    }
}
