package com.example.reservationservice.service.impl;


import com.example.commonsmodule.DTOs.ReservationDTO;
import com.example.commonsmodule.security.CommonSecurityUtils;
import com.example.reservationservice.entities.Reservation;
import com.example.reservationservice.repositories.ReservationRepository;
import com.example.reservationservice.services.impl.ReservationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceImplTesting {
    @Mock
    ReservationRepository reservationRepository;

    @Mock
    KafkaTemplate kafkaTemplate;

    @Mock
    CommonSecurityUtils commonSecurityUtils;



    @InjectMocks
    ReservationServiceImpl reservationService;
    ReservationDTO reservationDTO;
    Reservation reservation;

    @Spy
    private ModelMapper mapper;

    @BeforeEach
    void setUp() {
        reservationDTO = setReservationDTO();
        reservation = setReservation();
        this.mapper.createTypeMap(Reservation.class, ReservationDTO.class);
    }

    @Test
    public void testSave() {
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);
        ReservationDTO response = reservationService.save(reservationDTO);
        assertEquals(1L, response.getTutorUserId());
    }

//    @Test
//    public void test_findAll() {
//        List<Reservation> reservationList = getReservationList();
//        when(reservationRepository.findAll()).thenReturn(reservationList);//let  Mocking
//        assertEquals(2, reservationService.findAll().size());
//    }

    @Test
    public void findById() {
        when(reservationRepository.findById(reservationDTO.getReservationId())).thenReturn(Optional.of(reservation));
        reservationService.findById("100");
        verify(reservationRepository, times(1)).findById(reservationDTO.getReservationId());
    }


    private Reservation setReservation() {
        return Reservation.builder()
                .tutorUserId(1L)
                .tutorRequirementId("1")
                .reservationDate(LocalDate.of(2022, 01, 22))
                .reservationStatus(true)
                .build();
    }

    private ReservationDTO setReservationDTO() {
        return ReservationDTO.builder()
                .reservationId("100")
                .tutorUserId(1L)
                .tutorRequirementId("1")
                .reservationDate(LocalDate.of(2022, 01, 22))
                .reservationStatus(true)
                .build();
    }

    public List<Reservation> getReservationList() {
        return Arrays.asList(
                Reservation.builder()
                        .reservationId("100")
                        .tutorUserId(101L)
                        .tutorRequirementId("python")
                        .reservationDate(LocalDate.of(2022, 02, 11))
                        .reservationStatus(true)
                        .build(),
                Reservation.builder()
                        .reservationId("100")
                        .tutorUserId(101L)
                        .tutorRequirementId("python")
                        .reservationDate(LocalDate.of(2022, 02, 11))
                        .reservationStatus(true)
                        .build()

        );

    }

    @Test
    public void testJunit_Cases() {
        String name = "Pradip";
        assertEquals("Pradip", name);
    }

}
