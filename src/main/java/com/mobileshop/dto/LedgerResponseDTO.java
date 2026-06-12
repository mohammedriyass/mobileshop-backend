package com.mobileshop.dto;

import com.mobileshop.entity.Category;
import com.mobileshop.entity.EntryType;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class LedgerResponseDTO {
    private Long id;
    private EntryType entryType;
    private Category category;
    private BigDecimal amount;
    private String description;
    private LocalDate entryDate;
    private Long referenceId;
    private String itemName;
    private Integer quantity;
    private BigDecimal buyingPrice;
    private BigDecimal sellingPrice;
    private BigDecimal profit;
    private LocalDateTime createdAt;
}
