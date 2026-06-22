package com.mobileshop.repository;

import com.mobileshop.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findAllByOrderByCreatedAtDesc();

    Optional<Invoice> findByOrderId(Long orderId);

    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);

}
