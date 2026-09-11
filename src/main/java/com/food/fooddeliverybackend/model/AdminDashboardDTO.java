package com.food.fooddeliverybackend.model;

import com.food.fooddeliverybackend.enums.OrderStatus;
import com.food.fooddeliverybackend.enums.UserRoles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class AdminDashboardDTO {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlatformStatsDTO {
        private double totalGmv;
        private double platformRevenue; // Commission cut
        private long totalOrders;
        private long activeRestaurants;
        private long totalUsers;
        private long activeDeliveryAgents;
        private long totalDiningBookings;
        private long totalEventTickets;

        // Breakdown counts
        private Map<String, Long> ordersByStatus;
        private Map<String, Long> usersByRole;

        // Visual charts
        private List<MonthlyRevenueDTO> monthlyRevenue;
        private List<RecentActivityDTO> recentActivity;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MonthlyRevenueDTO {
        private String month;
        private double gmv;
        private double revenue;
        private long orderCount;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecentActivityDTO {
        private String id;
        private String type; // ORDER, RESTAURANT, USER, DINING, EVENT
        private String title;
        private String description;
        private String timestamp;
        private String status;
        private Double amount;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RestaurantManagementDTO {
        private Long id;
        private String restaurantName;
        private String address;
        private String phone;
        private double rating;
        private String ownerName;
        private String ownerEmail;
        private int totalMenuDishes;
        private int totalOrders;
        private double totalRevenue;
        private double commissionPercentage;
        private String status; // ACTIVE, PENDING_REVIEW, SUSPENDED
        private String imageUrl;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserManagementDTO {
        private Long id;
        private String fullName;
        private String username;
        private String email;
        private UserRoles role;
        private boolean active;
        private LocalDateTime createdAt;
        private int ordersCount;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlatformOrderDTO {
        private Long id;
        private String customerName;
        private String customerEmail;
        private String customerPhone;
        private String restaurantName;
        private Long restaurantId;
        private double totalAmount;
        private OrderStatus status;
        private String paymentMethod;
        private String paymentStatus;
        private String deliveryAddress;
        private LocalDateTime orderDate;
        private String deliveryAgentName;
        private Long deliveryAgentId;
        private int itemsCount;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SystemHealthDTO {
        private String status; // HEALTHY, DEGRADED, DOWN
        private long uptimeSeconds;
        private double cpuLoadPercent;
        private long usedMemoryMb;
        private long maxMemoryMb;
        private String databaseStatus;
        private int activeHttpConnections;
        private String serverTimestamp;
    }
}
