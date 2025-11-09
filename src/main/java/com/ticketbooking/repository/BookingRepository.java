package com.ticketbooking.repository;

import com.ticketbooking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    
    List<Booking> findByCustomerEmail(String email);
    
    Optional<Booking> findByBookingReference(String bookingReference);
    
    List<Booking> findByEventId(Long eventId);
    
    List<Booking> findByStatus(Booking.BookingStatus status);
}
