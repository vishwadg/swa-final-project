package com.example.commonsmodule.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long orderId;
    private Long userId;
    private Double totalAmount;
    private List<OrderItemDTO> orderItemList;
    private ShippingDTO shippingDTO;
}
