package com.example.foodhub.dto;
import lombok.Data;

@Data
public class DashboardStatsDTO {
    private long totalUsers;
    private long totalRestaurants;
    private long totalFoods;
    private long todayOrders;
    private long pendingRestaurants;
    private double revenueToday;
}