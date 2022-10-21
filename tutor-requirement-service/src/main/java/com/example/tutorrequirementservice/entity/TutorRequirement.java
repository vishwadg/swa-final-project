package com.example.tutorrequirementservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TutorRequirement {
    @Id
    private String id;
    private String title;
    private String description;
    private String studentId;
}
