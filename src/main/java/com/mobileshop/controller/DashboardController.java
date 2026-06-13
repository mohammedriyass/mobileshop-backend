package com.mobileshop.controller;

import com.mobileshop.dto.DashboardResponseDTO;
import com.mobileshop.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/today")
    public ResponseEntity<DashboardResponseDTO> getTodaySummary() {
        return ResponseEntity.ok(dashboardService.getTodaySummary());
    }

    @GetMapping("/month")
    public ResponseEntity<DashboardResponseDTO> getMonthSummary() {
        return ResponseEntity.ok(dashboardService.getMonthSummary());
    }

    @GetMapping("/year")
    public ResponseEntity<DashboardResponseDTO> getYearSummary() {
        return ResponseEntity.ok(dashboardService.getYearSummary());
    }

    @GetMapping("/custom")
    public ResponseEntity<DashboardResponseDTO> getCustomRangeSummary(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(dashboardService.getSummaryByDateRange(startDate, endDate));
    }
}