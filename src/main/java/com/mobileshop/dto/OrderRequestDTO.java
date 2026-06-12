package com.mobileshop.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class OrderRequestDTO {

    @NotNull(message = "Mobile ID is required")
    private Long mobileId;

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotBlank(message = "Phone is requied")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone must be 10 digits")
    private String customerPhone;

    @NotBlank(message = "Address is required")
    private String customerAddress;

    @NotBlank(message = "Payment method is required")
    private String paymentMethod;
}
