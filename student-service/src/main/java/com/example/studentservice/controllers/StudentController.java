package com.example.studentservice.controllers;

import com.example.commonsmodule.DTOs.StudentDTO;
import com.example.studentservice.services.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

//    @PostMapping
//    public StudentDTO save(@RequestBody StudentDTO payload){
//        log.info("Creating Student");
//        return studentService.save(payload);
//    }

    @GetMapping
    public List<StudentDTO> findAll(){
        log.info("Finding Student");
        return studentService.findAll();
    }

    @GetMapping("/{id}")
    public StudentDTO findOne(@PathVariable String id){
        log.info("Finding a Student");
        return studentService.findOne(id);
    }
}
