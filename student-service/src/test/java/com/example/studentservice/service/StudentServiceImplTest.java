package com.example.studentservice.service;


import com.example.studentservice.entities.DTOs.StudentDTO;
import com.example.studentservice.entities.Student;
import com.example.studentservice.repositories.StudentRepository;
import com.example.studentservice.services.impl.StudentServiceImpl;
import kafka.Kafka;
import org.apache.catalina.mapper.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceImplTest {
    @Mock
    StudentRepository studentRepository;

    @Mock
    KafkaTemplate kafkaTemplate;
    @InjectMocks
    StudentServiceImpl studentService;
    StudentDTO studentDTO;

    Student student;
    @Spy
    private ModelMapper mapperM;



    @BeforeEach()
    public void setUp() {
        studentDTO=setStudentDTO();
        student=setStudent();
        this.mapperM.createTypeMap(Student.class,StudentDTO.class);
    }

    private Student setStudent() {
        return Student.builder()
                .name("Pradip")
                .email("p@gmail.com")
                .address("fairfield,Iowa")
                .phone("9849303934")
                .description("Pradip live in fairfield and interested in SWA.")
                .build();

    }

    private StudentDTO setStudentDTO() {
        return StudentDTO.builder()
                .name("Pradip")
                .email("p@gmail.com")
                .address("fairfield,Iowa")
                .phone("9849303934")
                .description("Pradip live in fairfield and interested in SWA.")
                .build();
    }

    @Test
    public void findAllStudents() {
        List<Student> studentList = getStudents();
        when(studentRepository.findAll()).thenReturn(studentList);
        assertEquals(2,studentService.findAll().size());
        //List<StudentDTO> studentList1 = studentService.findAll();
        //assertEquals(studentList1.size(), 1);
        //verify(studentRepository, times(1)).findAll();
    }

    @Test
    void createStudent() {
        Student studentToSave = Student.builder()
                .name("Pradip")
                .email("p@gmail.com")
                .address("fairfield,Iowa")
                .phone("9849303934")
                .description("Pradip live in fairfield and interested in SWA.")
                .build();
        when(studentRepository.save(any())).thenReturn(studentToSave);
        StudentDTO studentDTO1 = studentService.save(studentDTO);
        assertNotNull(studentDTO1);
    }

    private List<Student> getStudents() {
//        StudentDTO student1 = new StudentDTO();
//        List<StudentDTO> studentList = new ArrayList<>();
//        studentList.add(student1);
//        return studentList;
        return Arrays.asList(Student.builder()
                .id("1")
                        .name("pradip")
                        .email("pradip@gmail.com")
                        .address("IA")
                        .phone("9849")
                        .description("info")
                .build(),
                Student.builder()
                        .id("1")
                        .name("pradip")
                        .email("pradip@gmail.com")
                        .address("IA")
                        .phone("9849")
                        .description("info")
                        .build()
                );
    }

}
