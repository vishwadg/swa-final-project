package com.example.commonsmodule.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
    private String reservationId;
    private Long tutorUserId;;
    private String tutorRequirementId;
    private String reservationDate;
    private boolean reservationStatus;
    private Long studentUserId;
    private String tutorRequirementTitle;
    private String tutorRequirementDesc;
}