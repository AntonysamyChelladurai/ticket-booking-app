package com.ticketbooking.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticketbooking.dto.BookingRequest;
import com.ticketbooking.dto.BookingResponse;
import com.ticketbooking.entity.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AIBookingService {
    
    private final TicketBookingAssistant assistant;
    private final EventService eventService;
    private final BookingService bookingService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    /**
     * Process natural language query for ticket booking
     */
    public String processNaturalLanguageQuery(String userQuery) {
        try {
            log.info("Processing natural language query: {}", userQuery);
            
            // Analyze the query to understand intent
            String searchAnalysis = assistant.analyzeSearchQuery(userQuery);
            log.debug("Search analysis: {}", searchAnalysis);
            
            JsonNode analysisNode = objectMapper.readTree(searchAnalysis);
            String searchType = analysisNode.get("searchType").asText();
            String searchValue = analysisNode.has("searchValue") ? 
                    analysisNode.get("searchValue").asText() : "";
            
            // Search for events based on analysis
            List<Event> events = searchEvents(searchType, searchValue);
            
            // Generate AI response with event information
            String eventInfo = formatEventsForAI(events);
            String aiResponse = assistant.chat(
                    "User asked: " + userQuery + 
                    "\n\nAvailable events:\n" + eventInfo +
                    "\n\nProvide a helpful response about these events."
            );
            
            return aiResponse;
            
        } catch (Exception e) {
            log.error("Error processing query", e);
            return "I apologize, but I encountered an error processing your request. Please try again or contact support.";
        }
    }
    
    /**
     * Process booking request using natural language
     */
    public BookingResponse processNaturalLanguageBooking(String userMessage, Long eventId) {
        try {
            log.info("Processing natural language booking: {}", userMessage);
            
            // Extract booking information from natural language
            String extractedInfo = assistant.extractBookingInfo(userMessage);
            log.debug("Extracted booking info: {}", extractedInfo);
            
            JsonNode infoNode = objectMapper.readTree(extractedInfo);
            
            // Create booking request
            BookingRequest request = new BookingRequest();
            request.setEventId(eventId);
            
            if (infoNode.has("numberOfTickets") && !infoNode.get("numberOfTickets").isNull()) {
                request.setNumberOfTickets(infoNode.get("numberOfTickets").asInt());
            } else {
                request.setNumberOfTickets(1); // Default
            }
            
            if (infoNode.has("customerName") && !infoNode.get("customerName").isNull()) {
                request.setCustomerName(infoNode.get("customerName").asText());
            } else {
                throw new RuntimeException("Customer name is required");
            }
            
            if (infoNode.has("customerEmail") && !infoNode.get("customerEmail").isNull()) {
                request.setCustomerEmail(infoNode.get("customerEmail").asText());
            } else {
                throw new RuntimeException("Customer email is required");
            }
            
            // Create the booking
            return bookingService.createBooking(request);
            
        } catch (Exception e) {
            log.error("Error processing booking", e);
            throw new RuntimeException("Failed to process booking: " + e.getMessage());
        }
    }
    
    /**
     * Get AI-powered event recommendations
     */
    public String getEventRecommendations(String preferences) {
        List<Event> allEvents = eventService.getAvailableEvents();
        String eventInfo = formatEventsForAI(allEvents);
        
        String query = "Based on user preferences: " + preferences +
                "\n\nAvailable events:\n" + eventInfo +
                "\n\nRecommend the best events for this user and explain why.";
        
        return assistant.chat(query);
    }
    
    private List<Event> searchEvents(String searchType, String searchValue) {
        return switch (searchType) {
            case "NAME" -> eventService.searchEventsByName(searchValue);
            case "CATEGORY" -> eventService.getEventsByCategory(searchValue);
            case "VENUE" -> eventService.searchEventsByVenue(searchValue);
            case "DATE" -> eventService.getUpcomingEvents();
            default -> eventService.getAvailableEvents();
        };
    }
    
    private String formatEventsForAI(List<Event> events) {
        if (events.isEmpty()) {
            return "No events found.";
        }
        
        StringBuilder sb = new StringBuilder();
        for (Event event : events) {
            sb.append(String.format(
                    "- %s at %s on %s (Category: %s, Price: $%.2f, Available Seats: %d)\n",
                    event.getName(),
                    event.getVenue(),
                    event.getEventDate(),
                    event.getCategory(),
                    event.getTicketPrice(),
                    event.getAvailableSeats()
            ));
        }
        return sb.toString();
    }
}
