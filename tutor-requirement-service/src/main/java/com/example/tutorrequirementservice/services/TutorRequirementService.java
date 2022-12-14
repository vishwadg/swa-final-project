package com.example.tutorrequirementservice.services;

import com.example.commonsmodule.DTOs.TutorRequirementDTO;
import com.example.commonsmodule.security.CommonSecurityUtils;
import com.example.tutorrequirementservice.entity.TutorRequirement;
import com.example.tutorrequirementservice.repositories.TutorRequirementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TutorRequirementService {

    @Value("${spring.kafka.custom.tutor_requirement_topic}")
    private String tutorRequirementTopic;
    @Value("${spring.kafka.custom.notification-topic}")
    private String notificationTopic;
    private final KafkaTemplate<String, TutorRequirementDTO> kafkaTutorRequirementTemplate;

    private final TutorRequirementRepository tutorRequirementRepository;
    private final ModelMapper modelMapper;


    public TutorRequirementDTO save(TutorRequirementDTO payload) {
        Optional<Long> studentUserId = CommonSecurityUtils.getCurrentUserId();
        payload.setStudentUserId(studentUserId.get());
        payload.setPostedDate(LocalDate.now().toString());
        TutorRequirement tutorRequirement = modelMapper.map(payload, TutorRequirement.class);
        tutorRequirement = tutorRequirementRepository.save(tutorRequirement);
        log.info("Tutor Requirements created Successfully!");
        TutorRequirementDTO tutorRequirementDTO = modelMapper.map(tutorRequirement, TutorRequirementDTO.class);

        kafkaTutorRequirementTemplate.send(tutorRequirementTopic, tutorRequirementDTO);
        return tutorRequirementDTO;
    }

    public List<TutorRequirementDTO> findAllByStudentUserId() {
        Long userId = CommonSecurityUtils.getCurrentUserId().get();
        return tutorRequirementRepository.findAllByStudentUserId(userId)
                .stream().map(tr -> modelMapper.map(tr, TutorRequirementDTO.class))
                .collect(Collectors.toList());
    }

    public List<TutorRequirementDTO> findAll() {
        return tutorRequirementRepository.findAll()
                .stream().map(tr -> modelMapper.map(tr, TutorRequirementDTO.class))
                .collect(Collectors.toList());
    }

    public TutorRequirementDTO findOne(String id) {
        return modelMapper.map(tutorRequirementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invalid ID!")), TutorRequirementDTO.class);
    }
}
