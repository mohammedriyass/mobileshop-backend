package com.mobileshop.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ledger")
public class Ledger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "entry_type", nullable = false)
    private EntryType entryType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(columnDefinition = "TEXT")
    private String Description;

    @Column(name = "entry_date", nullable = false)
    private LocalDate entryDate;

    @Column(name = "reference_id")
    private Long referenceId;

    @Column(name = "item_name")
    private String itemName;

    private Integer quantity;

    @Column(name = "buying_price", precision = 10, scale = 2)
    private BigDecimal buyingPrice;

    @Column(name = "selling_price", precision = 10, scale = 2)
    private BigDecimal sellingPrice;

    @Column(precision = 10, scale = 2)
    private BigDecimal profit;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (entryDate == null) {
            entryDate = LocalDate.now();
        }
        if (sellingPrice != null && buyingPrice != null) {
            profit = sellingPrice.subtract(buyingPrice);
            if (quantity != null) {
                profit = profit.multiply(BigDecimal.valueOf(quantity));
            }
        }
    }

}
