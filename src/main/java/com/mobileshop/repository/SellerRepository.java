package com.mobileshop.repository;

import com.mobileshop.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    Optional<Seller> findByPhone(String phone);

    boolean existsByPhone(String phone);

}
