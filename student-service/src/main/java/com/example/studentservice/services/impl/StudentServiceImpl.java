package com.example.studentservice.services.impl;

import com.example.studentservice.entities.DTOs.StudentDTO;
import com.example.studentservice.entities.Student;
import com.example.studentservice.repositories.StudentRepository;
import com.example.studentservice.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    @Override
    public StudentDTO save(StudentDTO payload) {
        return modelMapper.map(studentRepository.save(modelMapper.map(payload, Student.class)), StudentDTO.class);
    }

    @Override
    public List<StudentDTO> findAll() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(s -> modelMapper.map(s, StudentDTO.class)).collect(Collectors.toList());
    }

    @Override
    public StudentDTO findOne(String id) {
        return modelMapper.map(
                studentRepository.findById(id).orElseThrow(() -> new RuntimeException("No Such Student!"))
                , StudentDTO.class
        );
    }
}
