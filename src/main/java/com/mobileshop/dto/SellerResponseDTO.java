package com.mobileshop.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SellerResponseDTO {
    private Long id;
    private String name;
    private String phone;
    private String address;
    private String idProof;
    private String idProofType;
    private Integer totalPhoneSold;
    private LocalDateTime createdAt;

}
