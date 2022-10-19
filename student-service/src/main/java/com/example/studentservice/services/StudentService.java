package com.example.studentservice.services;

import com.example.studentservice.entities.DTOs.StudentDTO;

import java.util.List;

public interface StudentService {
    StudentDTO save(StudentDTO payload);

    List<StudentDTO> findAll();

    StudentDTO findOne(String id);
}
