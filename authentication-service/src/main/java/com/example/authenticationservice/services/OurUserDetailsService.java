package com.example.authenticationservice.services;

import com.example.authenticationservice.entities.User;
import com.example.authenticationservice.repositories.UserRepository;
import com.example.commonsmodule.security.OurUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OurUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("No Such User!"));
        return new OurUserDetails(user.getId(), user.getUsername(), user.getPassword(), user.getRoles());
    }
}
