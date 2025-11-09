package com.ticketbooking.config;

import com.ticketbooking.entity.Event;
import com.ticketbooking.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {
    
    private final EventRepository eventRepository;
    
    @Override
    public void run(String... args) {
        log.info("Initializing sample events...");
        
        List<Event> events = Arrays.asList(
                createEvent(
                        "Rock Concert: The Legends",
                        "Madison Square Garden",
                        LocalDateTime.now().plusDays(15),
                        120.00,
                        5000,
                        "CONCERT",
                        "An unforgettable night with legendary rock bands performing their greatest hits."
                ),
                createEvent(
                        "NBA Finals Game 7",
                        "Staples Center",
                        LocalDateTime.now().plusDays(30),
                        250.00,
                        20000,
                        "SPORTS",
                        "Witness history in the making at the most anticipated basketball game of the year."
                ),
                createEvent(
                        "Shakespeare's Hamlet",
                        "Broadway Theater",
                        LocalDateTime.now().plusDays(10),
                        85.00,
                        800,
                        "THEATER",
                        "A modern adaptation of Shakespeare's classic tragedy performed by award-winning actors."
                ),
                createEvent(
                        "Tech Innovation Summit 2025",
                        "Convention Center",
                        LocalDateTime.now().plusDays(45),
                        500.00,
                        3000,
                        "CONFERENCE",
                        "Join industry leaders and innovators for three days of insights into the future of technology."
                ),
                createEvent(
                        "Summer Music Festival",
                        "Central Park",
                        LocalDateTime.now().plusDays(60),
                        75.00,
                        10000,
                        "FESTIVAL",
                        "A weekend of amazing music featuring 50+ artists across multiple stages."
                ),
                createEvent(
                        "Classical Symphony Night",
                        "Carnegie Hall",
                        LocalDateTime.now().plusDays(20),
                        95.00,
                        2500,
                        "CONCERT",
                        "Experience the beauty of classical music with a world-renowned symphony orchestra."
                ),
                createEvent(
                        "Comedy Night Live",
                        "Comedy Club Downtown",
                        LocalDateTime.now().plusDays(7),
                        45.00,
                        300,
                        "THEATER",
                        "Laugh out loud with top comedians performing their best routines."
                ),
                createEvent(
                        "FIFA World Cup Qualifier",
                        "National Stadium",
                        LocalDateTime.now().plusDays(25),
                        150.00,
                        60000,
                        "SPORTS",
                        "Don't miss this crucial World Cup qualifying match!"
                )
        );
        
        eventRepository.saveAll(events);
        log.info("Sample events initialized successfully!");
    }
    
    private Event createEvent(String name, String venue, LocalDateTime eventDate, 
                             Double price, Integer seats, String category, String description) {
        Event event = new Event();
        event.setName(name);
        event.setVenue(venue);
        event.setEventDate(eventDate);
        event.setTicketPrice(price);
        event.setTotalSeats(seats);
        event.setAvailableSeats(seats);
        event.setCategory(category);
        event.setDescription(description);
        return event;
    }
}
