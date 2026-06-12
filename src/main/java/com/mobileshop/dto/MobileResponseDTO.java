package com.mobileshop.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class MobileResponseDTO {

    private Long id;
    private String brand;
    private String model;
    private String color;
    private String storage;
    private String ram;
    private String condition;
    private BigDecimal buyingPrice;
    private BigDecimal sellingPrice;
    private BigDecimal profit;
    private String imeiNumber1;
    private String imeiNumber2;
    private String imageUrl;
    private String description;
    private String status;
    private Long sellerId;
    private LocalDateTime createdAt;

}
