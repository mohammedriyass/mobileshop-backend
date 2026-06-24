package com.mobileshop.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class PaymentOrderResponseDTO {

    private String razorpayOrderId;
    private BigDecimal amount;
    private String currency;
    private String keyId;
    private String customerName;
    private String customerPhone;
    private String customerEmail;

}
