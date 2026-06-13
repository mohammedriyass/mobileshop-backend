package com.mobileshop.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class DashboardResponseDTO {

    private BigDecimal totalRevenue;
    private BigDecimal totalProfit;
    private BigDecimal mobileSalesAmount;
    private BigDecimal mobileSalesProfit;
    private Long mobileSalesCount;
    private BigDecimal accessorySalesAmount;
    private BigDecimal accessorySalesProfit;
    private Long AccessorySalesCount;
    private BigDecimal totalExpenses;
    private Long totalOrders;

}
