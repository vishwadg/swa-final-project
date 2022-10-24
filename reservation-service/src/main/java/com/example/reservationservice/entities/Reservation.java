package com.example.reservationservice.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Reservation")
public class Reservation {
    @Id
    private String reservationId;
    private Long tutorUserId;
    private Long studentUserId;
    private String tutorRequirementId;
    private LocalDate reservationDate;
    private boolean reservationStatus;
}
