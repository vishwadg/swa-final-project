package com.example.tutorrequirementsearchservice.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TutorRequirementDTO {
    private String id;
    private String title;
    private String description;
}
