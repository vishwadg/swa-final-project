package com.example.authenticationservice.service;


import com.example.authenticationservice.repositories.UserRepository;
import com.example.authenticationservice.services.impl.AuthenticationServiceImpl;
import com.example.commonsmodule.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;


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

    private ModelMapper modelMapper = new ModelMapper();


    @BeforeEach()
    public void setUp(){

    }

//    @Test
//    public void testLoginUser_thenResultAuthenticationResponse() throws JsonProcessingException
//    {
//
//        UserLoginDTO userLoginDTO=new UserLoginDTO();
//        userLoginDTO.setUsername(email);
//        userLoginDTO.setPassword(password);
//
//        String generatedToken ="generated-jwt-token";
//        TokenResponse tokenResponse=new TokenResponse();
//        tokenResponse.setToken(generatedToken);
//
//        UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(),userLoginDTO.getPassword());
//        when(authenticationManager.authenticate(any())).thenReturn(authenticationToken);
//        when(jwtTokenProvider.createToken(authenticationToken)).thenReturn(generatedToken);
//        assertEquals(authenticationService.login(userLoginDTO), tokenResponse);
//    }
//
//    @Test
//    public void testRegisterUser_thenResultUserDTO(){
//        CreatedUserRequestDTO createdUserRequestDTO=new CreatedUserRequestDTO();
//        createdUserRequestDTO.setUsername(email);
//        createdUserRequestDTO.setPassword("password");
//
//        String encodePassword="encoded-pass";
//        User user=new User();
//        user.setUsername(createdUserRequestDTO.getUsername());
//        user.setPassword(encodePassword);
//        user.setRoles(Set.of(UserRole.ROLE_TUTOR));
//
//        UserDTO userDTO=new UserDTO();
//        userDTO.setId(user.getId());
//        userDTO.setUsername(user.getUsername());
//        userDTO.setPassword(encodePassword);
//
////        when(passwordEncoder.encode(password)).thenReturn(encodePassword);
//        when(userRepository.save(user)).thenReturn(user);
//        assertEquals(authenticationService.register(modelMapper.map(createdUserRequestDTO,UserDTO.class)),userDTO);
//    }


}
