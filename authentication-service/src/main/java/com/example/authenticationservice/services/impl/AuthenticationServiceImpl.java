package com.example.authenticationservice.services.impl;

import com.example.authenticationservice.entities.DTOs.TokenResponse;
import com.example.commonsmodule.DTOs.StudentDTO;
import com.example.commonsmodule.DTOs.TutorDTO;
import com.example.commonsmodule.DTOs.UserDTO;
import com.example.authenticationservice.entities.DTOs.UserLoginDTO;
import com.example.authenticationservice.entities.User;
import com.example.authenticationservice.repositories.UserRepository;
import com.example.authenticationservice.services.AuthenticationService;
import com.example.commonsmodule.security.JwtTokenProvider;
import com.example.commonsmodule.security.enums.UserRole;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
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

    @Value("${spring.kafka.custom.tutor-signup-topic}")
    private String tutorSignupTopic;
    @Value("${spring.kafka.custom.student-signup-topic}")
    private String studentSignupTopic;

    @Value("${spring.kafka.custom.student-detail-after-reservation-email-topic}")
    private String studentDetailReservationTopic;
    @Value("${spring.kafka.custom.reservation-approved-email-topic}")
    private String reservationApprovedEmailTopic;
    private final KafkaTemplate<String, UserDTO> kafkaTemplate;

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
        user.setRoles(Set.of(payload.getRole()));
        User savedUser = userRepository.save(user);
        user.setPassword(null);
        payload.setPassword(null);
        if(payload.getRole().equals(UserRole.ROLE_TUTOR))
            kafkaTemplate.send(tutorSignupTopic, payload);
        else
            kafkaTemplate.send(studentSignupTopic, payload);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public UserDTO getUserByUserId(Long id) {
        User user = userRepository.findUserById(id);
        user.setPassword(null);
        kafkaTemplate.send(studentDetailReservationTopic, modelMapper.map(user, UserDTO.class));
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO sendReservationApprovedEmailToTutor(Long tutorUserId) {
        User user = userRepository.findUserById(tutorUserId);
        user.setPassword(null);
        kafkaTemplate.send(reservationApprovedEmailTopic, modelMapper.map(user, UserDTO.class));
        return modelMapper.map(user, UserDTO.class);
    }
}
