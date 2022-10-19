package com.example.authenticationservice.services.impl;

import com.example.authenticationservice.entities.DTOs.TokenResponse;
import com.example.authenticationservice.entities.DTOs.UserDTO;
import com.example.authenticationservice.entities.DTOs.UserLoginDTO;
import com.example.authenticationservice.entities.User;
import com.example.authenticationservice.repositories.UserRepository;
import com.example.authenticationservice.services.AuthenticationService;
import com.example.commonsmodule.security.JwtTokenProvider;
import com.example.commonsmodule.security.enums.UserRole;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;;
    private final ModelMapper modelMapper = new ModelMapper();
    @Override
    public TokenResponse login(UserLoginDTO payload) throws JsonProcessingException {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(payload.getUsername(),
                        payload.getPassword()));
        String token = jwtTokenProvider.createToken(authentication);
        TokenResponse tokenRes = new TokenResponse();
        tokenRes.setToken(token);
        return tokenRes;
    }

    @Override
    public UserDTO register(UserDTO payload) {
        User user = modelMapper.map(payload, User.class);
        user.setRoles(Set.of(UserRole.ROLE_TUTOR));
        User savedUser = userRepository.save(user);
        user.setPassword(null);
        return modelMapper.map(savedUser, UserDTO.class);
    }
}
