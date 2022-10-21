package com.example.tutorrequirementsearchservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TutorRequirement {
    private String id;
    private String title;
    private String description;
}
