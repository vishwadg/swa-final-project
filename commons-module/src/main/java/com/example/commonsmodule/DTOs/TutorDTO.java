package com.example.commonsmodule.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TutorDTO {
    private String tutorId;
    private String name;
    private String email;
    private String address;
    private String expertise;
    private String shortInfo;
}
