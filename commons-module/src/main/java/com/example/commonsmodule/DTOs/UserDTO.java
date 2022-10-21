package com.example.commonsmodule.DTOs;

import com.example.commonsmodule.security.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private UserRole role;

    private String name;
    private String email;
    private String address;
    private String phone;
    private String description;

    private String expertise;
    private String shortInfo;
}
