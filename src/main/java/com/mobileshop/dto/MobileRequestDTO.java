package com.mobileshop.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class MobileRequestDTO {

    @NotBlank(message = "Brand is Required")
    private String brand;

    @NotBlank(message = "Model is Required")
    private String model;

    private String color;
    private String storage;
    private String ram;

    @NotBlank(message = "Condition is Required")
    private String condition;

    @NotNull(message = "Buying Price is Required")
    private BigDecimal buyingPrice;

    @NotNull(message = "Selling Price is Required")
    private BigDecimal sellingPrice;

    @NotBlank(message = "IMEI Number 1 is Required")
    @Size(min = 15, max = 15, message = "IMEI must be 15 digits")
    private String imeiNumber1;

    private String imeiNumber2;
    private String imageUrl;
    private String description;

    @NotNull(message = "Seller ID is Required")
    private Long sellerId;
}
