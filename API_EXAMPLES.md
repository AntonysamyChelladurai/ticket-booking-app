# API Request Examples

This file contains example requests you can use to test the Ticket Booking Application.

## Prerequisites
- Application running on http://localhost:8080
- OpenAI API key configured

---

## 1. Event Management

### Get All Events
```bash
curl -X GET http://localhost:8080/api/events
```

### Get Event by ID
```bash
curl -X GET http://localhost:8080/api/events/1
```

### Create New Event
```bash
curl -X POST http://localhost:8080/api/events \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Jazz Night in the Park",
    "venue": "Central Park Amphitheater",
    "eventDate": "2025-12-20T20:00:00",
    "ticketPrice": 65.00,
    "totalSeats": 1500,
    "category": "CONCERT",
    "description": "An evening of smooth jazz under the stars"
  }'
```

### Search Events by Name
```bash
curl -X GET "http://localhost:8080/api/events/search?name=concert"
```

### Get Events by Category
```bash
curl -X GET http://localhost:8080/api/events/category/CONCERT
curl -X GET http://localhost:8080/api/events/category/SPORTS
curl -X GET http://localhost:8080/api/events/category/THEATER
```

### Get Upcoming Events
```bash
curl -X GET http://localhost:8080/api/events/upcoming
```

### Get Available Events
```bash
curl -X GET http://localhost:8080/api/events/available
```

---

## 2. Traditional Booking

### Create Booking
```bash
curl -X POST http://localhost:8080/api/bookings \
  -H "Content-Type: application/json" \
  -d '{
    "eventId": 1,
    "customerName": "Alice Johnson",
    "customerEmail": "alice@example.com",
    "numberOfTickets": 2
  }'
```

### Get Booking by Reference (replace BK-XXXXXXXX with actual reference)
```bash
curl -X GET http://localhost:8080/api/bookings/reference/BK-12345678
```

### Get All Bookings for an Email
```bash
curl -X GET http://localhost:8080/api/bookings/email/alice@example.com
```

### Cancel Booking (replace BK-XXXXXXXX with actual reference)
```bash
curl -X DELETE http://localhost:8080/api/bookings/reference/BK-12345678
```

---

## 3. AI-Powered Features

### Chat with AI Assistant - General Queries

**Find all concerts:**
```bash
curl -X POST http://localhost:8080/api/ai/chat \
  -H "Content-Type: application/json" \
  -d '{"message": "What concerts are available?"}'
```

**Search by venue:**
```bash
curl -X POST http://localhost:8080/api/ai/chat \
  -H "Content-Type: application/json" \
  -d '{"message": "Show me events at Madison Square Garden"}'
```

**Find sports events:**
```bash
curl -X POST http://localhost:8080/api/ai/chat \
  -H "Content-Type: application/json" \
  -d '{"message": "I want to see sports events"}'
```

**Ask about specific event:**
```bash
curl -X POST http://localhost:8080/api/ai/chat \
  -H "Content-Type: application/json" \
  -d '{"message": "Tell me about the Rock Concert event"}'
```

**Check available seats:**
```bash
curl -X POST http://localhost:8080/api/ai/chat \
  -H "Content-Type: application/json" \
  -d '{"message": "How many seats are available for the NBA Finals?"}'
```

### AI-Powered Booking

**Book with natural language (replace eventId with actual event ID):**
```bash
curl -X POST http://localhost:8080/api/ai/book/1 \
  -H "Content-Type: application/json" \
  -d '{"message": "Book 2 tickets for Bob Smith, email bob@example.com"}'
```

**Another booking example:**
```bash
curl -X POST http://localhost:8080/api/ai/book/3 \
  -H "Content-Type: application/json" \
  -d '{"message": "I need 4 tickets. My name is Carol Davis and email is carol.davis@example.com"}'
```

### Get AI Recommendations

**Rock music preference:**
```bash
curl -X POST http://localhost:8080/api/ai/recommendations \
  -H "Content-Type: application/json" \
  -d '{"preferences": "I love rock music and prefer large venues"}'
```

**Sports enthusiast:**
```bash
curl -X POST http://localhost:8080/api/ai/recommendations \
  -H "Content-Type: application/json" \
  -d '{"preferences": "I am a huge sports fan, especially basketball and soccer"}'
```

**Theater lover:**
```bash
curl -X POST http://localhost:8080/api/ai/recommendations \
  -H "Content-Type: application/json" \
  -d '{"preferences": "I enjoy theater shows and classical music performances"}'
```

**Budget conscious:**
```bash
curl -X POST http://localhost:8080/api/ai/recommendations \
  -H "Content-Type: application/json" \
  -d '{"preferences": "I am looking for affordable events under $100"}'
```

---

## 4. Complex Scenarios

### Scenario 1: Complete Booking Flow

**Step 1: Find events**
```bash
curl -X POST http://localhost:8080/api/ai/chat \
  -H "Content-Type: application/json" \
  -d '{"message": "Show me all available concerts"}'
```

**Step 2: Get details of specific event**
```bash
curl -X GET http://localhost:8080/api/events/1
```

**Step 3: Book tickets**
```bash
curl -X POST http://localhost:8080/api/bookings \
  -H "Content-Type: application/json" \
  -d '{
    "eventId": 1,
    "customerName": "David Lee",
    "customerEmail": "david@example.com",
    "numberOfTickets": 3
  }'
```

**Step 4: Verify booking**
```bash
curl -X GET http://localhost:8080/api/bookings/email/david@example.com
```

### Scenario 2: Natural Language Booking

**Ask AI for help:**
```bash
curl -X POST http://localhost:8080/api/ai/chat \
  -H "Content-Type: application/json" \
  -d '{"message": "I want to book tickets for a concert. What options do I have?"}'
```

**Book using natural language:**
```bash
curl -X POST http://localhost:8080/api/ai/book/1 \
  -H "Content-Type: application/json" \
  -d '{"message": "Book 2 tickets for Emma Wilson, email emma.wilson@example.com"}'
```

### Scenario 3: Event Search and Recommendation

**Search for specific type:**
```bash
curl -X POST http://localhost:8080/api/ai/chat \
  -H "Content-Type: application/json" \
  -d '{"message": "What theater shows are available in the next month?"}'
```

**Get personalized recommendations:**
```bash
curl -X POST http://localhost:8080/api/ai/recommendations \
  -H "Content-Type: application/json" \
  -d '{"preferences": "I like outdoor events and festivals with family-friendly atmosphere"}'
```

---

## 5. Testing AI Conversational Abilities

**Multi-turn conversation (simulate):**

```bash
# First message
curl -X POST http://localhost:8080/api/ai/chat \
  -H "Content-Type: application/json" \
  -d '{"message": "What events do you have?"}'

# Follow-up
curl -X POST http://localhost:8080/api/ai/chat \
  -H "Content-Type: application/json" \
  -d '{"message": "Tell me more about the concerts"}'

# Specific question
curl -X POST http://localhost:8080/api/ai/chat \
  -H "Content-Type: application/json" \
  -d '{"message": "Whats the price for the Rock Concert?"}'
```

---

## Notes

1. **Event IDs**: After getting events, note the `id` field to use in booking requests
2. **Booking References**: Save the `bookingReference` from booking responses to query/cancel bookings
3. **Email Format**: Must be valid email format for bookings
4. **Date Format**: Use ISO 8601 format: `YYYY-MM-DDTHH:mm:ss`
5. **Categories**: Valid categories are: CONCERT, SPORTS, THEATER, CONFERENCE, FESTIVAL

## Expected Responses

### Successful Booking Response
```json
{
  "bookingId": 1,
  "bookingReference": "BK-A1B2C3D4",
  "eventName": "Rock Concert: The Legends",
  "venue": "Madison Square Garden",
  "eventDate": "2025-11-23T19:00:00",
  "customerName": "Alice Johnson",
  "customerEmail": "alice@example.com",
  "numberOfTickets": 2,
  "totalAmount": 240.00,
  "bookingDate": "2025-11-08T10:30:00",
  "status": "CONFIRMED",
  "message": "Booking confirmed"
}
```

### AI Chat Response
```json
{
  "response": "I found 3 available concerts for you:\n1. Rock Concert: The Legends at Madison Square Garden on November 23, 2025 - $120 per ticket\n2. Classical Symphony Night at Carnegie Hall on November 28, 2025 - $95 per ticket\n3. Summer Music Festival at Central Park on January 7, 2026 - $75 per ticket\n\nWould you like to book tickets for any of these events?"
}
```

---

**Happy Testing! 🎫**
