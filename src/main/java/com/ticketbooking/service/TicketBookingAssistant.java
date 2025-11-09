package com.ticketbooking.service;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

/**
 * AI Assistant for ticket booking using Langchain4j
 * This interface will be automatically implemented by Langchain4j
 */
public interface TicketBookingAssistant {
    
    @SystemMessage("""
        You are a helpful ticket booking assistant. Your role is to help users find and book tickets for events.
        You can search for events by name, category, venue, or date.
        When users ask about booking tickets, extract the following information:
        - Event name or ID
        - Number of tickets
        - Customer name (if provided)
        - Customer email (if provided)
        
        Be friendly, concise, and helpful. If information is missing, politely ask for it.
        Always confirm booking details before proceeding.
        """)



    @UserMessage("{{userQuery}}")
    String chat(@V("userQuery") String userQuery);
    
    @SystemMessage("""
        Extract booking information from the user's message.
        Return a JSON object with the following fields:
        - eventName: string (name of the event)
        - numberOfTickets: integer (number of tickets to book)
        - customerName: string (customer's name)
        - customerEmail: string (customer's email)
        
        If any field is not mentioned, set it to null.
        Return ONLY the JSON object, no additional text.
        """)
    @UserMessage("{{userMessage}}")
    String extractBookingInfo(@V("userMessage") String userMessage);
    
    @SystemMessage("""
        Based on the user's query, determine what type of event search they want.
        Return a JSON object with:
        - searchType: string (NAME, CATEGORY, VENUE, DATE, or GENERAL)
        - searchValue: string (the value to search for)
        
        Categories can be: CONCERT, SPORTS, THEATER, CONFERENCE, FESTIVAL
        Return ONLY the JSON object, no additional text.
        """)
    @UserMessage("{{query}}")
    String analyzeSearchQuery(@V("query") String query);
}
