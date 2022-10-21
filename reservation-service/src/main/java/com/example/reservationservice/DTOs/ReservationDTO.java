package com.example.reservationservice.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
    private String reservationId;
    private String tutorId;
    private String tutorRequirementId;
    private LocalDate reservationDate;
    private boolean reservationStatus;
}
