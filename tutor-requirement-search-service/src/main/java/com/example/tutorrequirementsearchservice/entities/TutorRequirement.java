package com.example.tutorrequirementsearchservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TutorRequirement {
    private String id;
    private String title;
    private String description;
    private Long userId;
    private String postedDate;
}
