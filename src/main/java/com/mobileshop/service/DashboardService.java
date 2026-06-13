package com.mobileshop.service;

import com.mobileshop.dto.DashboardResponseDTO;
import com.mobileshop.entity.EntryType;
import com.mobileshop.repository.LedgerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;

@Service
public class DashboardService {

    @Autowired
    private LedgerRepository ledgerRepository;

    public DashboardResponseDTO getTodaySummary() {
        LocalDate today = LocalDate.now();
        return buildSummary(today, today);
    }

    public DashboardResponseDTO getMonthSummary() {
        LocalDate today = LocalDate.now();
        YearMonth yearMonth = YearMonth.from(today);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();
        return buildSummary(startDate, endDate);
    }

    public DashboardResponseDTO getYearSummary() {
        LocalDate today = LocalDate.now();
        LocalDate startDate = LocalDate.of(today.getYear(), 1, 1);
        LocalDate endDate = LocalDate.of(today.getYear(), 12, 31);
        return buildSummary(startDate, endDate);
    }

    public DashboardResponseDTO getSummaryByDateRange(LocalDate startDate, LocalDate endDate) {
        return buildSummary(startDate, endDate);
    }

    private DashboardResponseDTO buildSummary(LocalDate startDate, LocalDate endDate) {
        DashboardResponseDTO dto = new DashboardResponseDTO();

        BigDecimal mobileSalesAmount = ledgerRepository.sumAmountByEntryTypeAndDateRange(EntryType.MOBILE_SALE,
                startDate, endDate);
        BigDecimal mobileSalesProfit = ledgerRepository.sumProfitByEntryTypeAndDateRange(
                EntryType.MOBILE_SALE, startDate, endDate);
        Long mobileSalesCount = ledgerRepository.countByEntryTypeAndDateRange(
                EntryType.MOBILE_SALE, startDate, endDate);

        BigDecimal accessorySalesAmount = ledgerRepository.sumAmountByEntryTypeAndDateRange(
                EntryType.ACCESSORY_SALE, startDate, endDate);
        BigDecimal accessorySalesProfit = ledgerRepository.sumProfitByEntryTypeAndDateRange(
                EntryType.ACCESSORY_SALE, startDate, endDate);
        Long accessorySalesCount = ledgerRepository.countByEntryTypeAndDateRange(
                EntryType.ACCESSORY_SALE, startDate, endDate);

        BigDecimal totalExpenses = ledgerRepository.sumAmountByEntryTypeAndDateRange(
                EntryType.EXPENSE, startDate, endDate);

        BigDecimal totalRevenue = mobileSalesAmount.add(accessorySalesAmount);
        BigDecimal totalProfit = mobileSalesProfit.add(accessorySalesProfit).subtract(totalExpenses);
        Long totalOrders = mobileSalesCount + accessorySalesCount;

        dto.setTotalRevenue(totalRevenue);
        dto.setTotalProfit(totalProfit);
        dto.setMobileSalesAmount(mobileSalesAmount);
        dto.setMobileSalesProfit(mobileSalesProfit);
        dto.setMobileSalesCount(mobileSalesCount);
        dto.setAccessorySalesAmount(accessorySalesAmount);
        dto.setAccessorySalesProfit(accessorySalesProfit);
        dto.setAccessorySalesCount(accessorySalesCount);
        dto.setTotalExpenses(totalExpenses);
        dto.setTotalOrders(totalOrders);

        return dto;

    }

}
