package com.example.tutorservice.services;

import com.example.tutorservice.entities.DTOs.TutorDTO;
import com.example.tutorservice.entities.Tutor;
import com.example.tutorservice.repositories.TutorRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TutorServiceImpl implements TutorService {
    @Value("${spring.kafka.custom.tutor-topic}")
    private String tutorTopic;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private KafkaTemplate<String, TutorDTO> kafkaTutorTemplate;

    @Autowired
    private  ModelMapper modelMapper;

    @Override
    public TutorDTO signupTutor(TutorDTO tutorDTO) {
        log.info("Tutor sign up process started ....");
        Tutor tutor = new Tutor();
        tutor.setName(tutorDTO.getName());
        tutor.setEmail(tutorDTO.getEmail());
        tutor.setAddress(tutorDTO.getAddress());
        tutor.setExpertise(tutorDTO.getExpertise());
        tutor.setShortInfo(tutorDTO.getShortInfo());

        Tutor tutorRepo = tutorRepository.save(tutor);
        tutorDTO.setTutorId(tutorRepo.getTutorId());
        kafkaTutorTemplate.send(tutorTopic, tutorDTO);
        log.info("Success: Tutor data saved");
        return tutorDTO;

    }

    @Override
    public TutorDTO findById(String id) {
        Optional<Tutor> tutorOptional = tutorRepository.findById(id);
        Tutor tutor = tutorOptional.orElseThrow();

        if (tutor == null) {
            log.error("Failure: Tutor not found with id {}", id);
            throw new RuntimeException("Sorry, tutor not found in the system");
        }

        TutorDTO tutorDTO = new TutorDTO();
        tutorDTO.setTutorId(tutor.getTutorId());
        tutorDTO.setName(tutor.getName());
        tutorDTO.setEmail(tutor.getEmail());
        tutorDTO.setAddress(tutor.getAddress());
        tutorDTO.setExpertise(tutor.getExpertise());
        tutorDTO.setShortInfo(tutor.getShortInfo());
        log.info("Success: Tutor found with id {}", id);
        return tutorDTO;
    }

    @Override
    public List<TutorDTO> findAll() {
        List<Tutor> tutorList = tutorRepository.findAll();

        if (tutorList.isEmpty()) {
            log.info("Failure: Tutors are not found in the system");
            throw new RuntimeException("Sorry, tutors are not found in the system");
        }

        List<TutorDTO> tutorDTOList = tutorList.stream().map(
                tutor -> {
                    TutorDTO tutorDTO = new TutorDTO();
                    tutorDTO.setTutorId(tutor.getTutorId());
                    tutorDTO.setName(tutor.getName());
                    tutorDTO.setEmail(tutor.getEmail());
                    tutorDTO.setAddress(tutor.getAddress());
                    tutorDTO.setExpertise(tutor.getExpertise());
                    tutorDTO.setShortInfo(tutor.getShortInfo());
                    return tutorDTO;
                }).collect(Collectors.toList());
        log.info("Success: Tutors list found");
        return tutorDTOList;
    }
}
