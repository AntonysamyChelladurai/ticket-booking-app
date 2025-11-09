package com.ticketbooking.controller;

import com.ticketbooking.dto.BookingResponse;
import com.ticketbooking.service.AIBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AIController {
    
    private final AIBookingService aiBookingService;
    
    /**
     * Chat with AI assistant using natural language
     * Example: "Show me all concerts in New York"
     */
    @PostMapping("/chat")
    public ResponseEntity<Map<String, String>> chat(@RequestBody Map<String, String> request) {
        String query = request.get("message");
        String response = aiBookingService.processNaturalLanguageQuery(query);
        return ResponseEntity.ok(Map.of("response", response));
    }
    
    /**
     * Book tickets using natural language
     * Example: "Book 2 tickets for John Doe, email john@example.com"
     */
    @PostMapping("/book/{eventId}")
    public ResponseEntity<BookingResponse> bookWithNaturalLanguage(
            @PathVariable Long eventId,
            @RequestBody Map<String, String> request) {
        String message = request.get("message");
        BookingResponse response = aiBookingService.processNaturalLanguageBooking(message, eventId);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Get AI-powered event recommendations
     * Example: "I like rock music and outdoor venues"
     */
    @PostMapping("/recommendations")
    public ResponseEntity<Map<String, String>> getRecommendations(@RequestBody Map<String, String> request) {
        String preferences = request.get("preferences");
        String recommendations = aiBookingService.getEventRecommendations(preferences);
        return ResponseEntity.ok(Map.of("recommendations", recommendations));
    }
}
