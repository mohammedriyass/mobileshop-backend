package com.mobileshop.repository;

import com.mobileshop.entity.Mobile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

@Repository
public interface MobileRepository extends JpaRepository<Mobile, Long> {

    List<Mobile> findByBrand(String Brand);

    List<Mobile> findByStatus(String status);

    boolean existsByImeiNumber1(String imeiNumber1);

    boolean existsByImeiNumber2(String imeiNumber2);

    Optional<Mobile> findByImeiNumber1(String imeiNumber1);

    List<Mobile> findByBrandContainingIgnoreCaseOrModelContainingIgnoreCase(String brand, String model);

    List<Mobile> findByStatusOrderByCreatedAtDesc(String status);

    List<Mobile> findBySellerId(Long sellerId);

}
