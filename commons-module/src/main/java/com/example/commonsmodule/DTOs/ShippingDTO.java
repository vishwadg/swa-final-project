package com.example.commonsmodule.DTOs;

import lombok.Data;

@Data
public class ShippingDTO {
    private Long shippingId;
    private Long userId;
    private Long orderId;
    private String city;
    private String state;
    private String stAddress;
    private Integer zip;
}
