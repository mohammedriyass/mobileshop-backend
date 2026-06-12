package com.mobileshop.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderResponseDTO {

    private Long id;
    private Long MobileId;
    private String mobileBrand;
    private String mobileModel;
    private String customerName;
    private String customerPhone;
    private String customerAddress;
    private String paymentMethod;
    private String PaymentStatus;
    private String orderStatus;
    private String razorpayOrderId;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;

}
