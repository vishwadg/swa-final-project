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
@Document
public class Tutor {
    @Id
    private String tutorId;
    private String name;
    private String email;
    private String address;
    private String expertise;
    private String shortInfo;
}
