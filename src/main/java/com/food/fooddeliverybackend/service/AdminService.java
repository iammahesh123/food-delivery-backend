package com.food.fooddeliverybackend.service;

import com.food.fooddeliverybackend.enums.OrderStatus;
import com.food.fooddeliverybackend.enums.UserRoles;
import com.food.fooddeliverybackend.model.AdminDashboardDTO.*;

import java.util.List;

public interface AdminService {

    PlatformStatsDTO getDashboardStats();

    List<RestaurantManagementDTO> getAllRestaurantsForAdmin();

    RestaurantManagementDTO updateRestaurantStatus(Long id, String status);

    RestaurantManagementDTO updateRestaurantCommission(Long id, double commissionPercentage);

    void deleteRestaurant(Long id);

    List<UserManagementDTO> getAllUsersForAdmin(UserRoles role, String searchQuery);

    UserManagementDTO updateUserRole(Long id, UserRoles role);

    UserManagementDTO toggleUserActiveStatus(Long id, boolean active);

    void deleteUser(Long id);

    List<PlatformOrderDTO> getAllPlatformOrders(OrderStatus status, String search);

    PlatformOrderDTO updateOrderStatus(Long id, OrderStatus status);

    PlatformOrderDTO assignDeliveryAgent(Long orderId, Long agentId);

    SystemHealthDTO getSystemHealth();
}
