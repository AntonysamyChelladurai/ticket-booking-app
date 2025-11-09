package com.ticketbooking.repository;

import com.ticketbooking.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    
    List<Event> findByCategory(String category);
    
    List<Event> findByEventDateBetween(LocalDateTime start, LocalDateTime end);
    
    List<Event> findByVenueContainingIgnoreCase(String venue);
    
    List<Event> findByNameContainingIgnoreCase(String name);
    
    List<Event> findByAvailableSeatsGreaterThan(Integer seats);
}
