package com.example.tutorservice.service;

import com.example.tutorservice.entities.DTOs.TutorDTO;
import com.example.tutorservice.entities.Tutor;
import com.example.tutorservice.repositories.TutorRepository;
import com.example.tutorservice.services.impl.TutorServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TutorServiceTest {
    private Tutor newTutor;

    @Mock
    private KafkaTemplate<String, TutorDTO> kafkaTemplate;

    @Mock
    private TutorRepository tutorRepository;
    @InjectMocks
    private TutorServiceImpl tutorService;

    @Spy
    private ModelMapper modelMapper;


    @BeforeEach
    public void beforeEach() {
        newTutor = createTutor();

    }

    private Tutor createTutor() {
        Tutor tutor = new Tutor();
        tutor.setName("Pradip");
        tutor.setEmail("testIt@gmail.com");
        tutor.setPhone("984930");
        tutor.setAddress("fairfield");
        tutor.setExpertise("python");
        tutor.setShortInfo(" pradip test is 9849 fairfield address");
        return tutor;
    }

    @Test
    public void createTutorProfile() {
        newTutor.setTutorId(null);
        when(tutorRepository.save(any(Tutor.class))).thenReturn(newTutor);
        TutorDTO tutorDTO = new TutorDTO();
        tutorDTO.setName("Pradip");
        tutorDTO.setEmail("testIt@gmail.com");
        tutorDTO.setAddress("fairfield");
        tutorDTO.setExpertise("python");
        tutorDTO.setShortInfo(" pradip test is 9849 fairfield address");
        Assertions.assertEquals(tutorDTO, tutorService.signupTutor(tutorDTO));
    }

    @Test
    public void findTutorByIdTest() {
        Optional<Tutor> tutor = Optional.ofNullable(newTutor);
        when(tutorRepository.findById("")).thenReturn(tutor);
        TutorDTO tutorDTO = new TutorDTO();
        tutorDTO.setName("Pradip");
        tutorDTO.setEmail("testIt@gmail.com");
        tutorDTO.setAddress("fairfield");
        tutorDTO.setExpertise("python");
        tutorDTO.setShortInfo(" pradip test is 9849 fairfield address");
        Assertions.assertEquals(tutorDTO, tutorService.findById(""));
    }

    @Test
    public void findAllTutorTest() {
        List<Tutor> tutorList = List.of(newTutor);
        TutorDTO tutorDTO = new TutorDTO();
        tutorDTO.setName("Pradip");
        tutorDTO.setEmail("testIt@gmail.com");
        tutorDTO.setAddress("fairfield");
        tutorDTO.setExpertise("python");
        tutorDTO.setShortInfo(" pradip test is 9849 fairfield address");
        List<TutorDTO> tutorDTOList = List.of(tutorDTO);
        when(tutorRepository.findAll()).thenReturn(tutorList);
        Assertions.assertEquals(tutorDTOList,tutorService.findAll());
    }
}
