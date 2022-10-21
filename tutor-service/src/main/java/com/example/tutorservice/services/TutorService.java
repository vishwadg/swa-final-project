package com.example.tutorservice.services;

import com.example.tutorservice.entities.DTOs.TutorDTO;

import java.util.List;

public interface TutorService {
    TutorDTO signupTutor(TutorDTO tutorDTO);

    TutorDTO findById(String id);

    List<TutorDTO> findAll();
}
