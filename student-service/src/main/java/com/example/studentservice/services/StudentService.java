package com.example.studentservice.services;


import com.example.commonsmodule.DTOs.StudentDTO;

import java.util.List;

public interface StudentService {
    StudentDTO save(StudentDTO payload);

    List<StudentDTO> findAll();

    StudentDTO findOne(String id);
}
