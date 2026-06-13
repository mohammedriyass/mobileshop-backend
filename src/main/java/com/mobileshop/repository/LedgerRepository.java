package com.mobileshop.repository;

import com.mobileshop.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface LedgerRepository extends JpaRepository<Ledger, Long> {

    List<Ledger> findAllByOrderByEntryDateDesc();

    List<Ledger> findByEntryType(EntryType entryType);

    List<Ledger> findByCategory(Category category);

    List<Ledger> findByEntryDateBetween(LocalDate startDate, LocalDate endDate);

    List<Ledger> findByEntryDate(LocalDate date);

    @Query("SELECT COALESCE(SUM(l.profit), 0) FROM Ledger l " +
            "WHERE l.entryType = :entryType " +
            "AND l.entryDate BETWEEN :startDate AND :endDate")
    BigDecimal sumProfitByEntryTypeAndDateRange(
            @Param("entryType") EntryType entryType,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query("SELECT COALESCE(SUM(l.amount), 0) FROM Ledger l " +
            "WHERE l.entryType = :entryType " +
            "AND l.entryDate BETWEEN :startDate AND :endDate")
    BigDecimal sumAmountByEntryTypeAndDateRange(
            @Param("entryType") EntryType entryType,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(l) FROM Ledger l " +
            "WHERE l.entryType = :entryType " +
            "AND l.entryDate BETWEEN :startDate AND :endDate")
    Long countByEntryTypeAndDateRange(
            @Param("entryType") EntryType entryType,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

}
