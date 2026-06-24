package com.mobileshop.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentOrderRequestDTO {

    @NotNull(message = "Order Id is Required")
    private Long OrderId;

}
