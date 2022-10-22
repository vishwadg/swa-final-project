package com.example.tutorservice.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "Tutors")
public class Tutor {
    @Id
    private String tutorId;
    private String expertise;
    private String shortInfo;
    private Long userId;
}
