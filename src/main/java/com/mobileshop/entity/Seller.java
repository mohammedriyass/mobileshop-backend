package com.mobileshop.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "sellers")
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(name = "id_proof")
    private String idProof;

    @Column(name = "id_proof_type")
    private String idProofType;

    @Column(name = "total_phone_sold")
    private Integer totalPhoneSold = 0;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
