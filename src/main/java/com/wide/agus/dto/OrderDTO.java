package com.wide.agus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private UUID id;
    private String name;
    private String productType;
    private BigDecimal price;
    private int qty;
    private BigDecimal total;
}
