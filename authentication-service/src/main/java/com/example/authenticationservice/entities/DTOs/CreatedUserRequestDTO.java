package com.example.authenticationservice.entities.DTOs;

import lombok.Data;

@Data
public class CreatedUserRequestDTO {
    private String fullName;
    private String username;
    private String password;
}
