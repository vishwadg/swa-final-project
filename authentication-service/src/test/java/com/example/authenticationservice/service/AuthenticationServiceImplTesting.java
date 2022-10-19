package com.example.authenticationservice.service;


import com.example.authenticationservice.repositories.UserRepository;
import com.example.authenticationservice.services.impl.AuthenticationServiceImpl;
import com.example.commonsmodule.security.JwtTokenProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceImplTesting {
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    private String email="user@email.com";
    private  String password="password";


    @BeforeEach()
    public void setUp(){

    }

    @Test
    public void testLoginUser_thenResultAuthenticationResponse() throws JsonProcessingException
    {
        LoginRequest loginRequestDTO=new LoginRequestDTO();
        loginRequestDTO.setUser


    }


}
