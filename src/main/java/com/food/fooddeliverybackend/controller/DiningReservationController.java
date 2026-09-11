package com.food.fooddeliverybackend.controller;

import com.food.fooddeliverybackend.model.DiningReservationRequestDto;
import com.food.fooddeliverybackend.model.DiningReservationResponseDto;
import com.food.fooddeliverybackend.model.ReservationStatusUpdateDto;
import com.food.fooddeliverybackend.service.DiningReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Tag(name = "Dining Reservations Controller", description = "Endpoints for Table Reservations and Dining Management")
@RestController
@RequestMapping("/dining/reservations")
public class DiningReservationController {

    private final DiningReservationService diningReservationService;

    public DiningReservationController(DiningReservationService diningReservationService) {
        this.diningReservationService = diningReservationService;
    }

    @Operation(summary = "Create a new table reservation")
    @PostMapping
    public ResponseEntity<DiningReservationResponseDto> createReservation(@RequestBody DiningReservationRequestDto requestDto) {
        DiningReservationResponseDto response = diningReservationService.createReservation(requestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Get reservation by ID")
    @GetMapping("/{id}")
    public ResponseEntity<DiningReservationResponseDto> getReservationById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(diningReservationService.getReservationById(id));
    }

    @Operation(summary = "Get reservation by booking reference")
    @GetMapping("/ref/{reference}")
    public ResponseEntity<DiningReservationResponseDto> getReservationByReference(@PathVariable("reference") String reference) {
        return ResponseEntity.ok(diningReservationService.getReservationByReference(reference));
    }

    @Operation(summary = "Get user's reservations")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DiningReservationResponseDto>> getUserReservations(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(diningReservationService.getReservationsByUser(userId));
    }

    @Operation(summary = "Get restaurant reservations for merchant")
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<DiningReservationResponseDto>> getRestaurantReservations(
            @PathVariable("restaurantId") Long restaurantId,
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        if (date != null) {
            return ResponseEntity.ok(diningReservationService.getReservationsByRestaurantAndDate(restaurantId, date));
        }
        return ResponseEntity.ok(diningReservationService.getReservationsByRestaurant(restaurantId));
    }

    @Operation(summary = "Update reservation status and table assignment")
    @PutMapping("/{id}/status")
    public ResponseEntity<DiningReservationResponseDto> updateStatus(
            @PathVariable("id") Long id,
            @RequestBody ReservationStatusUpdateDto updateDto) {
        return ResponseEntity.ok(diningReservationService.updateReservationStatus(id, updateDto));
    }

    @Operation(summary = "Cancel a reservation")
    @PutMapping("/{id}/cancel")
    public ResponseEntity<DiningReservationResponseDto> cancelReservation(@PathVariable("id") Long id) {
        return ResponseEntity.ok(diningReservationService.cancelReservation(id));
    }

    @Operation(summary = "Settle dining bill at table")
    @PostMapping("/{id}/pay-bill")
    public ResponseEntity<DiningReservationResponseDto> settleBill(
            @PathVariable("id") Long id,
            @RequestBody Map<String, Double> payload) {
        Double amount = payload.getOrDefault("billAmount", 0.0);
        return ResponseEntity.ok(diningReservationService.settleBill(id, amount));
    }
}
