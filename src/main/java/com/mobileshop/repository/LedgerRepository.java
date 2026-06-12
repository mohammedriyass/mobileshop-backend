package com.mobileshop.repository;

import com.mobileshop.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface LedgerRepository extends JpaRepository<Ledger, Long> {

    List<Ledger> findAllByOrderByEntryDateDesc();

    List<Ledger> findByEntryType(EntryType entryType);

    List<Ledger> findByCategory(Category category);

    List<Ledger> findByEntryDateBetween(LocalDate startDate, LocalDate endDate);

    List<Ledger> findByEntryDate(LocalDate date);

}
