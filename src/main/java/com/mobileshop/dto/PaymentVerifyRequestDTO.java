package com.mobileshop.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Data;

@Data
public class PaymentVerifyRequestDTO {

    @NotNull(message = "Order Id is required")
    private Long orderId;

    @NotBlank(message = "RazorPay order ID is required")
    private String razorpayOrderId;

    @NotBlank(message = "Razorpay payment ID is required")
    private String razorpayPaymentId;

    @NotBlank(message = "RazorPay signature is Required")
    private String razorpaySignature;
}
