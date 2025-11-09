package com.ticketbooking.service;

import com.ticketbooking.entity.Event;
import com.ticketbooking.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {
    
    private final EventRepository eventRepository;
    
    public Event createEvent(Event event) {
        event.setAvailableSeats(event.getTotalSeats());
        return eventRepository.save(event);
    }
    
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }
    
    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with ID: " + id));
    }
    
    public List<Event> searchEventsByName(String name) {
        return eventRepository.findByNameContainingIgnoreCase(name);
    }
    
    public List<Event> getEventsByCategory(String category) {
        return eventRepository.findByCategory(category.toUpperCase());
    }
    
    public List<Event> searchEventsByVenue(String venue) {
        return eventRepository.findByVenueContainingIgnoreCase(venue);
    }
    
    public List<Event> getUpcomingEvents() {
        return eventRepository.findByEventDateBetween(
                LocalDateTime.now(),
                LocalDateTime.now().plusMonths(3)
        );
    }
    
    public List<Event> getAvailableEvents() {
        return eventRepository.findByAvailableSeatsGreaterThan(0);
    }
    
    public Event updateEvent(Long id, Event eventDetails) {
        Event event = getEventById(id);
        event.setName(eventDetails.getName());
        event.setVenue(eventDetails.getVenue());
        event.setEventDate(eventDetails.getEventDate());
        event.setTicketPrice(eventDetails.getTicketPrice());
        event.setDescription(eventDetails.getDescription());
        event.setCategory(eventDetails.getCategory());
        return eventRepository.save(event);
    }
    
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}
