package com.food.fooddeliverybackend.controller;

import com.food.fooddeliverybackend.enums.EventCategory;
import com.food.fooddeliverybackend.model.EventBookingRequestDto;
import com.food.fooddeliverybackend.model.EventTicketResponseDto;
import com.food.fooddeliverybackend.model.LiveEventDto;
import com.food.fooddeliverybackend.service.LiveEventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Live Events & Ticketing Controller", description = "Endpoints for Concerts, Food Festivals, and Live Event Ticketing")
@RestController
@RequestMapping("/api/events")
public class LiveEventController {

    private final LiveEventService liveEventService;

    public LiveEventController(LiveEventService liveEventService) {
        this.liveEventService = liveEventService;
    }

    @Operation(summary = "Get all live events")
    @GetMapping
    public ResponseEntity<List<LiveEventDto>> getAllEvents() {
        return ResponseEntity.ok(liveEventService.getAllEvents());
    }

    @Operation(summary = "Get live events by category")
    @GetMapping("/category/{category}")
    public ResponseEntity<List<LiveEventDto>> getEventsByCategory(@PathVariable("category") EventCategory category) {
        return ResponseEntity.ok(liveEventService.getEventsByCategory(category));
    }

    @Operation(summary = "Get event by ID")
    @GetMapping("/{id}")
    public ResponseEntity<LiveEventDto> getEventById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(liveEventService.getEventById(id));
    }

    @Operation(summary = "Book event tickets")
    @PostMapping("/tickets")
    public ResponseEntity<EventTicketResponseDto> bookTicket(@RequestBody EventBookingRequestDto requestDto) {
        EventTicketResponseDto response = liveEventService.bookTicket(requestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Get user's event tickets")
    @GetMapping("/tickets/user/{userId}")
    public ResponseEntity<List<EventTicketResponseDto>> getUserTickets(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(liveEventService.getTicketsByUser(userId));
    }

    @Operation(summary = "Get ticket by booking reference")
    @GetMapping("/tickets/ref/{reference}")
    public ResponseEntity<EventTicketResponseDto> getTicketByReference(@PathVariable("reference") String reference) {
        return ResponseEntity.ok(liveEventService.getTicketByReference(reference));
    }

    @Operation(summary = "Get attendee tickets for event organizer")
    @GetMapping("/{eventId}/tickets")
    public ResponseEntity<List<EventTicketResponseDto>> getEventTickets(@PathVariable("eventId") Long eventId) {
        return ResponseEntity.ok(liveEventService.getTicketsByEvent(eventId));
    }

    @Operation(summary = "Check in attendee ticket at venue gate")
    @PutMapping("/tickets/{ticketId}/check-in")
    public ResponseEntity<EventTicketResponseDto> checkInTicket(@PathVariable("ticketId") Long ticketId) {
        return ResponseEntity.ok(liveEventService.checkInTicket(ticketId));
    }

    @Operation(summary = "Cancel event ticket")
    @PutMapping("/tickets/{ticketId}/cancel")
    public ResponseEntity<EventTicketResponseDto> cancelTicket(@PathVariable("ticketId") Long ticketId) {
        return ResponseEntity.ok(liveEventService.cancelTicket(ticketId));
    }
}
