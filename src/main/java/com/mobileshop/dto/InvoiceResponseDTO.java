package com.mobileshop.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class InvoiceResponseDTO {

    private Long id;
    private Long orderId;
    private String invoiceNumber;
    private String customerName;
    private String customerPhone;
    private String mobileDetails;
    private BigDecimal totalAmount;
    private String pdfPath;
    private LocalDateTime createdAt;

}
