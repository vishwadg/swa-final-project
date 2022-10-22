package com.example.studentservice.consumers;

import com.example.commonsmodule.DTOs.StudentDTO;
import com.example.commonsmodule.DTOs.TutorRequirementDTO;
import com.example.commonsmodule.DTOs.UserDTO;
import com.example.studentservice.services.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentKafkaConsumer {

    public final StudentService studentService;

    @KafkaListener(topics = {"${spring.kafka.custom.student-signup-topic}"}, containerFactory = "kafkaListenerJsonFactory",
        groupId = "${spring.kafka.consumer.group-id}", autoStartup = "${spring.kafka.custom.enable-listeners}")
    public void consumeStudentSignupDTO(UserDTO userDTO){
        log.info("Received Student SignUp {}", userDTO);
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setDescription(userDTO.getDescription());
        studentDTO.setUserId(userDTO.getId());
        studentDTO = studentService.save(studentDTO);
        log.info("Successfully Saved Student {}", studentDTO);
    }
}
