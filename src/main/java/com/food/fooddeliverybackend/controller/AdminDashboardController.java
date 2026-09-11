package com.food.fooddeliverybackend.controller;

import com.food.fooddeliverybackend.enums.OrderStatus;
import com.food.fooddeliverybackend.enums.UserRoles;
import com.food.fooddeliverybackend.model.AdminDashboardDTO.*;
import com.food.fooddeliverybackend.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "Platform Admin Controller", description = "Endpoints for Platform Super Admin Management")
@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminDashboardController {

    private final AdminService adminService;

    public AdminDashboardController(AdminService adminService) {
        this.adminService = adminService;
    }

    @Operation(summary = "Get high-level platform executive statistics and analytics")
    @GetMapping("/dashboard/stats")
    public ResponseEntity<PlatformStatsDTO> getDashboardStats() {
        return ResponseEntity.ok(adminService.getDashboardStats());
    }

    // ----------------- Restaurants Management -----------------
    @Operation(summary = "Get all platform restaurants with operational metrics")
    @GetMapping("/restaurants")
    public ResponseEntity<List<RestaurantManagementDTO>> getAllRestaurants() {
        return ResponseEntity.ok(adminService.getAllRestaurantsForAdmin());
    }

    @Operation(summary = "Update restaurant operating status (ACTIVE, PENDING_REVIEW, SUSPENDED)")
    @PatchMapping("/restaurants/{id}/status")
    public ResponseEntity<RestaurantManagementDTO> updateRestaurantStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        String status = body.getOrDefault("status", "ACTIVE");
        return ResponseEntity.ok(adminService.updateRestaurantStatus(id, status));
    }

    @Operation(summary = "Update platform commission rate for a restaurant")
    @PatchMapping("/restaurants/{id}/commission")
    public ResponseEntity<RestaurantManagementDTO> updateRestaurantCommission(
            @PathVariable Long id,
            @RequestBody Map<String, Double> body) {
        Double commission = body.getOrDefault("commissionPercentage", 15.0);
        return ResponseEntity.ok(adminService.updateRestaurantCommission(id, commission));
    }

    @Operation(summary = "Delete restaurant by ID")
    @DeleteMapping("/restaurants/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        adminService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }

    // ----------------- User Management -----------------
    @Operation(summary = "Get platform users with optional role and keyword filtering")
    @GetMapping("/users")
    public ResponseEntity<List<UserManagementDTO>> getAllUsers(
            @RequestParam(required = false) UserRoles role,
            @RequestParam(required = false) String search) {
        return ResponseEntity.ok(adminService.getAllUsersForAdmin(role, search));
    }

    @Operation(summary = "Update user role by ID")
    @PatchMapping("/users/{id}/role")
    public ResponseEntity<UserManagementDTO> updateUserRole(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        UserRoles role = UserRoles.valueOf(body.get("role"));
        return ResponseEntity.ok(adminService.updateUserRole(id, role));
    }

    @Operation(summary = "Enable or suspend user account")
    @PatchMapping("/users/{id}/status")
    public ResponseEntity<UserManagementDTO> toggleUserStatus(
            @PathVariable Long id,
            @RequestBody Map<String, Boolean> body) {
        boolean active = body.getOrDefault("active", true);
        return ResponseEntity.ok(adminService.toggleUserActiveStatus(id, active));
    }

    @Operation(summary = "Delete user by ID")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // ----------------- Global Orders Oversight -----------------
    @Operation(summary = "Get all platform orders across all restaurants")
    @GetMapping("/orders")
    public ResponseEntity<List<PlatformOrderDTO>> getAllOrders(
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) String search) {
        return ResponseEntity.ok(adminService.getAllPlatformOrders(status, search));
    }

    @Operation(summary = "Admin override of order status")
    @PatchMapping("/orders/{id}/status")
    public ResponseEntity<PlatformOrderDTO> updateOrderStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        OrderStatus status = OrderStatus.valueOf(body.get("status"));
        return ResponseEntity.ok(adminService.updateOrderStatus(id, status));
    }

    @Operation(summary = "Assign or reassign delivery agent to an order")
    @PatchMapping("/orders/{id}/assign-driver")
    public ResponseEntity<PlatformOrderDTO> assignDriver(
            @PathVariable Long id,
            @RequestBody Map<String, Long> body) {
        Long driverId = body.get("driverId");
        return ResponseEntity.ok(adminService.assignDeliveryAgent(id, driverId));
    }

    // ----------------- System Health -----------------
    @Operation(summary = "Get platform system health, JVM metrics and database status")
    @GetMapping("/system/health")
    public ResponseEntity<SystemHealthDTO> getSystemHealth() {
        return ResponseEntity.ok(adminService.getSystemHealth());
    }
}
