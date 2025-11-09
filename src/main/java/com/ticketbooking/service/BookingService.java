package com.ticketbooking.service;

import com.ticketbooking.dto.BookingRequest;
import com.ticketbooking.dto.BookingResponse;
import com.ticketbooking.entity.Booking;
import com.ticketbooking.entity.Event;
import com.ticketbooking.repository.BookingRepository;
import com.ticketbooking.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {
    
    private final BookingRepository bookingRepository;
    private final EventRepository eventRepository;
    
    @Transactional
    public BookingResponse createBooking(BookingRequest request) {
        log.info("Creating booking for event: {}", request.getEventId());
        
        // Find event
        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found with ID: " + request.getEventId()));
        
        // Check available seats
        if (event.getAvailableSeats() < request.getNumberOfTickets()) {
            throw new RuntimeException("Not enough seats available. Available: " + event.getAvailableSeats());
        }
        
        // Create booking
        Booking booking = new Booking();
        booking.setEvent(event);
        booking.setCustomerName(request.getCustomerName());
        booking.setCustomerEmail(request.getCustomerEmail());
        booking.setNumberOfTickets(request.getNumberOfTickets());
        booking.setTotalAmount(event.getTicketPrice() * request.getNumberOfTickets());
        booking.setBookingDate(LocalDateTime.now());
        booking.setStatus(Booking.BookingStatus.CONFIRMED);
        booking.setBookingReference(generateBookingReference());
        
        // Update available seats
        event.setAvailableSeats(event.getAvailableSeats() - request.getNumberOfTickets());
        eventRepository.save(event);
        
        // Save booking
        booking = bookingRepository.save(booking);
        
        log.info("Booking created successfully: {}", booking.getBookingReference());
        
        return mapToResponse(booking);
    }
    
    public BookingResponse getBookingByReference(String reference) {
        Booking booking = bookingRepository.findByBookingReference(reference)
                .orElseThrow(() -> new RuntimeException("Booking not found with reference: " + reference));
        return mapToResponse(booking);
    }
    
    public List<BookingResponse> getBookingsByEmail(String email) {
        return bookingRepository.findByCustomerEmail(email)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    
    @Transactional
    public BookingResponse cancelBooking(String reference) {
        Booking booking = bookingRepository.findByBookingReference(reference)
                .orElseThrow(() -> new RuntimeException("Booking not found with reference: " + reference));
        
        if (booking.getStatus() == Booking.BookingStatus.CANCELLED) {
            throw new RuntimeException("Booking is already cancelled");
        }
        
        // Update booking status
        booking.setStatus(Booking.BookingStatus.CANCELLED);
        
        // Restore available seats
        Event event = booking.getEvent();
        event.setAvailableSeats(event.getAvailableSeats() + booking.getNumberOfTickets());
        eventRepository.save(event);
        
        booking = bookingRepository.save(booking);
        
        log.info("Booking cancelled: {}", reference);
        
        return mapToResponse(booking);
    }
    
    private BookingResponse mapToResponse(Booking booking) {
        BookingResponse response = new BookingResponse();
        response.setBookingId(booking.getId());
        response.setBookingReference(booking.getBookingReference());
        response.setEventName(booking.getEvent().getName());
        response.setVenue(booking.getEvent().getVenue());
        response.setEventDate(booking.getEvent().getEventDate());
        response.setCustomerName(booking.getCustomerName());
        response.setCustomerEmail(booking.getCustomerEmail());
        response.setNumberOfTickets(booking.getNumberOfTickets());
        response.setTotalAmount(booking.getTotalAmount());
        response.setBookingDate(booking.getBookingDate());
        response.setStatus(booking.getStatus());
        response.setMessage("Booking " + booking.getStatus().toString().toLowerCase());
        return response;
    }
    
    private String generateBookingReference() {
        return "BK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
