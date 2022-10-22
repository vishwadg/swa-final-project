package com.example.commonsmodule.DTOs;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ResponseDto {
    private int code;
    private String message;
}
