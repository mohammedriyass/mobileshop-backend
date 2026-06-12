package com.mobileshop.dto;

import com.mobileshop.entity.Category;
import com.mobileshop.entity.EntryType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class LedgerRequestDTO {

    @NotNull(message = "Entry type is required")
    private EntryType entryType;

    @NotNull(message = "Category is required")
    private Category category;

    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    private String description;
    private LocalDate entryDate;
    private Long referenceId;
    private String itemName;
    private Integer quantity;
    private BigDecimal buyingPrice;
    private BigDecimal sellingPrice;

}
