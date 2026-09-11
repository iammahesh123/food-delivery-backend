package com.food.fooddeliverybackend.service;

import com.food.fooddeliverybackend.enums.EventCategory;
import com.food.fooddeliverybackend.model.EventBookingRequestDto;
import com.food.fooddeliverybackend.model.EventTicketResponseDto;
import com.food.fooddeliverybackend.model.LiveEventDto;

import java.util.List;

public interface LiveEventService {
    List<LiveEventDto> getAllEvents();

    List<LiveEventDto> getEventsByCategory(EventCategory category);

    LiveEventDto getEventById(Long id);

    EventTicketResponseDto bookTicket(EventBookingRequestDto requestDto);

    EventTicketResponseDto getTicketById(Long ticketId);

    EventTicketResponseDto getTicketByReference(String bookingReference);

    List<EventTicketResponseDto> getTicketsByUser(Long userId);

    List<EventTicketResponseDto> getTicketsByEvent(Long eventId);

    EventTicketResponseDto checkInTicket(Long ticketId);

    EventTicketResponseDto cancelTicket(Long ticketId);
}
