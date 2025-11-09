package com.ticketbooking.dto;

import com.ticketbooking.entity.Booking;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {
    
    private Long bookingId;
    private String bookingReference;
    private String eventName;
    private String venue;
    private LocalDateTime eventDate;
    private String customerName;
    private String customerEmail;
    private Integer numberOfTickets;
    private Double totalAmount;
    private LocalDateTime bookingDate;
    private Booking.BookingStatus status;
    private String message;
}
