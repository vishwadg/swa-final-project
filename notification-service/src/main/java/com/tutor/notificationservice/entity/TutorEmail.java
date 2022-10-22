package com.tutor.notificationservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TutorEmail {
    private String to;
    private String subject;
    private String text;
}
