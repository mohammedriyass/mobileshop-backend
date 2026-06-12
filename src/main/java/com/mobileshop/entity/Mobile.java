package com.mobileshop.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "mobiles")
public class Mobile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    private String color;
    private String storage;
    private String ram;

    @Column(nullable = false)
    private String condition;

    @Column(name = "buying_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal buyingPrice;

    @Column(name = "selling_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal sellingPrice;

    @Column(precision = 10, scale = 2)
    private BigDecimal profit;

    @Column(name = "imei_number_1", nullable = false, unique = true)
    private String imeiNumber1;

    @Column(name = "imei_number_2", unique = true)
    private String imeiNumber2;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String status = "AVAILABLE";

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (sellingPrice != null && buyingPrice != null) {
            profit = sellingPrice.subtract(buyingPrice);
        }
    }

    @PreUpdate
    protected void onUpdate() {
        if (sellingPrice != null && buyingPrice != null) {
            profit = sellingPrice.subtract(buyingPrice);
        }
    }
}
