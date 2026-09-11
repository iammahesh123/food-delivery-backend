package com.food.fooddeliverybackend.service.impl;

import com.food.fooddeliverybackend.entity.EventTicketEntity;
import com.food.fooddeliverybackend.entity.LiveEventEntity;
import com.food.fooddeliverybackend.enums.EventCategory;
import com.food.fooddeliverybackend.enums.TicketStatus;
import com.food.fooddeliverybackend.exception.ResourceNotFoundException;
import com.food.fooddeliverybackend.model.EventBookingRequestDto;
import com.food.fooddeliverybackend.model.EventTicketResponseDto;
import com.food.fooddeliverybackend.model.LiveEventDto;
import com.food.fooddeliverybackend.repository.EventTicketRepository;
import com.food.fooddeliverybackend.repository.LiveEventRepository;
import com.food.fooddeliverybackend.repository.UserRepository;
import com.food.fooddeliverybackend.service.LiveEventService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class LiveEventServiceImpl implements LiveEventService {

    private final LiveEventRepository liveEventRepository;
    private final EventTicketRepository eventTicketRepository;
    private final UserRepository userRepository;

    public LiveEventServiceImpl(LiveEventRepository liveEventRepository,
                                EventTicketRepository eventTicketRepository,
                                UserRepository userRepository) {
        this.liveEventRepository = liveEventRepository;
        this.eventTicketRepository = eventTicketRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<LiveEventDto> getAllEvents() {
        return liveEventRepository.findAll().stream()
                .map(this::mapEventToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LiveEventDto> getEventsByCategory(EventCategory category) {
        return liveEventRepository.findByCategory(category).stream()
                .map(this::mapEventToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public LiveEventDto getEventById(Long id) {
        LiveEventEntity event = liveEventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));
        return mapEventToDto(event);
    }

    @Override
    public EventTicketResponseDto bookTicket(EventBookingRequestDto requestDto) {
        LiveEventEntity event = liveEventRepository.findById(requestDto.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + requestDto.getEventId()));

        EventTicketEntity ticket = new EventTicketEntity();
        ticket.setEventEntity(event);

        if (requestDto.getUserId() != null) {
            userRepository.findById(requestDto.getUserId()).ifPresent(ticket::setUserEntity);
        }

        ticket.setTicketTier(requestDto.getTicketTier() != null ? requestDto.getTicketTier() : "General Entry");
        int qty = requestDto.getQuantity() > 0 ? requestDto.getQuantity() : 1;
        ticket.setQuantity(qty);
        ticket.setUnitPrice(requestDto.getUnitPrice());
        ticket.setTotalAmount(requestDto.getTotalAmount() > 0 ? requestDto.getTotalAmount() : requestDto.getUnitPrice() * qty);

        String ref = "TOM-EVT-" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        ticket.setBookingReference(ref);
        ticket.setAttendeeName(requestDto.getAttendeeName() != null ? requestDto.getAttendeeName() : "Valued Guest");
        ticket.setAttendeePhone(requestDto.getAttendeePhone() != null ? requestDto.getAttendeePhone() : "N/A");
        ticket.setAttendeeEmail(requestDto.getAttendeeEmail());
        ticket.setStatus(TicketStatus.CONFIRMED);
        ticket.setFoodVoucherIncluded(requestDto.isFoodVoucherIncluded());
        ticket.setQrCode(ref);
        ticket.setPurchaseDate(LocalDateTime.now());

        // Update event available seats
        if (event.getAvailableTickets() >= qty) {
            event.setAvailableTickets(event.getAvailableTickets() - qty);
            liveEventRepository.save(event);
        }

        EventTicketEntity saved = eventTicketRepository.save(ticket);
        return mapTicketToDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public EventTicketResponseDto getTicketById(Long ticketId) {
        EventTicketEntity ticket = eventTicketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + ticketId));
        return mapTicketToDto(ticket);
    }

    @Override
    @Transactional(readOnly = true)
    public EventTicketResponseDto getTicketByReference(String bookingReference) {
        EventTicketEntity ticket = eventTicketRepository.findByBookingReference(bookingReference)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with reference: " + bookingReference));
        return mapTicketToDto(ticket);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventTicketResponseDto> getTicketsByUser(Long userId) {
        return eventTicketRepository.findByUserEntityIdOrderByPurchaseDateDesc(userId).stream()
                .map(this::mapTicketToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventTicketResponseDto> getTicketsByEvent(Long eventId) {
        return eventTicketRepository.findByEventEntityIdOrderByPurchaseDateDesc(eventId).stream()
                .map(this::mapTicketToDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventTicketResponseDto checkInTicket(Long ticketId) {
        EventTicketEntity ticket = eventTicketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + ticketId));
        ticket.setStatus(TicketStatus.CHECKED_IN);
        EventTicketEntity updated = eventTicketRepository.save(ticket);
        return mapTicketToDto(updated);
    }

    @Override
    public EventTicketResponseDto cancelTicket(Long ticketId) {
        EventTicketEntity ticket = eventTicketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + ticketId));
        ticket.setStatus(TicketStatus.CANCELLED);
        EventTicketEntity updated = eventTicketRepository.save(ticket);
        return mapTicketToDto(updated);
    }

    private LiveEventDto mapEventToDto(LiveEventEntity e) {
        LiveEventDto dto = new LiveEventDto();
        dto.setId(e.getId());
        dto.setTitle(e.getTitle());
        dto.setTagline(e.getTagline());
        dto.setDescription(e.getDescription());
        dto.setCategory(e.getCategory());
        dto.setArtistName(e.getArtistName());
        dto.setVenueName(e.getVenueName());
        dto.setVenueAddress(e.getVenueAddress());
        dto.setEventDate(e.getEventDate());
        dto.setStartTime(e.getStartTime());
        dto.setEndTime(e.getEndTime());
        dto.setImageUrl(e.getImageUrl());
        dto.setBannerUrl(e.getBannerUrl());
        dto.setMinPrice(e.getMinPrice());
        dto.setTotalCapacity(e.getTotalCapacity());
        dto.setAvailableTickets(e.getAvailableTickets());
        dto.setFeatured(e.isFeatured());
        return dto;
    }

    private EventTicketResponseDto mapTicketToDto(EventTicketEntity t) {
        EventTicketResponseDto dto = new EventTicketResponseDto();
        dto.setId(t.getId());
        dto.setBookingReference(t.getBookingReference());
        if (t.getEventEntity() != null) {
            dto.setEventId(t.getEventEntity().getId());
            dto.setEventTitle(t.getEventEntity().getTitle());
            dto.setEventArtist(t.getEventEntity().getArtistName());
            dto.setEventVenue(t.getEventEntity().getVenueName());
            dto.setEventAddress(t.getEventEntity().getVenueAddress());
            dto.setEventDate(t.getEventEntity().getEventDate());
            dto.setEventStartTime(t.getEventEntity().getStartTime());
            dto.setEventImageUrl(t.getEventEntity().getImageUrl());
        }
        if (t.getUserEntity() != null) {
            dto.setUserId(t.getUserEntity().getId());
        }
        dto.setTicketTier(t.getTicketTier());
        dto.setQuantity(t.getQuantity());
        dto.setUnitPrice(t.getUnitPrice());
        dto.setTotalAmount(t.getTotalAmount());
        dto.setAttendeeName(t.getAttendeeName());
        dto.setAttendeePhone(t.getAttendeePhone());
        dto.setAttendeeEmail(t.getAttendeeEmail());
        dto.setStatus(t.getStatus());
        dto.setFoodVoucherIncluded(t.isFoodVoucherIncluded());
        dto.setQrCode(t.getQrCode());
        dto.setPurchaseDate(t.getPurchaseDate());
        return dto;
    }
}
