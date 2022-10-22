package com.example.studentservice.services.impl;

import com.example.commonsmodule.DTOs.StudentDTO;
import com.example.studentservice.entities.Student;
import com.example.studentservice.repositories.StudentRepository;
import com.example.studentservice.services.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    @Value("${spring.kafka.custom.student-signup-email-topic}")
    private String studentSignupEmailTopic;
    private final KafkaTemplate<String, StudentDTO> kafkaTemplate;

    @Override
    public StudentDTO save(StudentDTO payload) {
        Student student = studentRepository.save(modelMapper.map(payload, Student.class));
        log.info("Student Saved on Database. {}", student);
        kafkaTemplate.send(studentSignupEmailTopic, payload);
        log.info("Student Signup email request sent on kafka queue. {}", student);
        return modelMapper.map(student, StudentDTO.class);
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
