package com.example.commonsmodule.DTOs;

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
    private Long tutorUserId;;
    private String tutorRequirementId;
    private LocalDate reservationDate;
    private boolean reservationStatus;
}