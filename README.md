# Ticket Booking Application with Spring AI and Langchain4j

An intelligent ticket booking system powered by Spring Boot, Spring AI, and Langchain4j. This application allows users to book tickets for events using both traditional REST APIs and natural language interactions.

## Features

- 🎫 **Event Management**: Create, update, and manage events
- 📝 **Ticket Booking**: Book tickets for various events
- 🤖 **AI-Powered Search**: Search for events using natural language
- 💬 **Chat Assistant**: Interact with an AI assistant for booking help
- 🎯 **Smart Recommendations**: Get personalized event recommendations
- 🔍 **Advanced Search**: Search by name, category, venue, or date

## Tech Stack

- **Spring Boot 3.2.0**
- **Spring AI 1.0.0-M3**
- **Langchain4j 0.34.0**
- **H2 Database** (In-memory)
- **OpenAI GPT-4** (for AI features)
- **Maven**
- **Java 17**

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- OpenAI API Key (for AI features)

## Setup

### 1. Clone the Repository

```bash
git clone <repository-url>
cd ticket-booking-app
```

### 2. Configure OpenAI API Key

Edit `src/main/resources/application.properties` and add your OpenAI API key:

```properties
spring.ai.openai.api-key=your-openai-api-key-here
langchain4j.open-ai.chat-model.api-key=your-openai-api-key-here
```

Alternatively, set environment variable:

```bash
export OPENAI_API_KEY=your-openai-api-key-here
```

### 3. Build the Application

```bash
mvn clean install
```

### 4. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### Event Endpoints

#### Get All Events
```bash
GET /api/events
```

#### Get Event by ID
```bash
GET /api/events/{id}
```

#### Create Event
```bash
POST /api/events
Content-Type: application/json

{
  "name": "Summer Concert",
  "venue": "City Arena",
  "eventDate": "2025-12-15T19:00:00",
  "ticketPrice": 50.00,
  "totalSeats": 1000,
  "category": "CONCERT",
  "description": "Amazing summer concert"
}
```

#### Search Events
```bash
GET /api/events/search?name=concert
GET /api/events/category/CONCERT
GET /api/events/venue/arena
GET /api/events/upcoming
GET /api/events/available
```

### Booking Endpoints

#### Create Booking
```bash
POST /api/bookings
Content-Type: application/json

{
  "eventId": 1,
  "customerName": "John Doe",
  "customerEmail": "john@example.com",
  "numberOfTickets": 2
}
```

#### Get Booking by Reference
```bash
GET /api/bookings/reference/{bookingReference}
```

#### Get Bookings by Email
```bash
GET /api/bookings/email/{email}
```

#### Cancel Booking
```bash
DELETE /api/bookings/reference/{bookingReference}
```

### AI-Powered Endpoints

#### Chat with AI Assistant
```bash
POST /api/ai/chat
Content-Type: application/json

{
  "message": "Show me all concerts in Madison Square Garden"
}
```

#### Book Tickets with Natural Language
```bash
POST /api/ai/book/{eventId}
Content-Type: application/json

{
  "message": "Book 2 tickets for Jane Smith, email jane@example.com"
}
```

#### Get AI Recommendations
```bash
POST /api/ai/recommendations
Content-Type: application/json

{
  "preferences": "I love rock music and prefer indoor venues"
}
```

## Example Usage

### Using cURL

#### 1. Chat with AI to find events
```bash
curl -X POST http://localhost:8080/api/ai/chat \
  -H "Content-Type: application/json" \
  -d '{"message": "What concerts are available next month?"}'
```

#### 2. Get event recommendations
```bash
curl -X POST http://localhost:8080/api/ai/recommendations \
  -H "Content-Type: application/json" \
  -d '{"preferences": "I like sports events and theater shows"}'
```

#### 3. Traditional booking
```bash
curl -X POST http://localhost:8080/api/bookings \
  -H "Content-Type: application/json" \
  -d '{
    "eventId": 1,
    "customerName": "John Doe",
    "customerEmail": "john@example.com",
    "numberOfTickets": 2
  }'
```

#### 4. AI-powered booking
```bash
curl -X POST http://localhost:8080/api/ai/book/1 \
  -H "Content-Type: application/json" \
  -d '{"message": "Book 3 tickets for Sarah Johnson, email sarah@example.com"}'
```

### Using Postman

Import the following collection:

1. Create a new collection in Postman
2. Add the endpoints mentioned above
3. Set base URL to `http://localhost:8080`
4. Start testing!

## Sample Data

The application comes with pre-loaded sample events:

1. **Rock Concert: The Legends** - Madison Square Garden
2. **NBA Finals Game 7** - Staples Center
3. **Shakespeare's Hamlet** - Broadway Theater
4. **Tech Innovation Summit 2025** - Convention Center
5. **Summer Music Festival** - Central Park
6. **Classical Symphony Night** - Carnegie Hall
7. **Comedy Night Live** - Comedy Club Downtown
8. **FIFA World Cup Qualifier** - National Stadium

## Event Categories

- `CONCERT` - Music concerts
- `SPORTS` - Sporting events
- `THEATER` - Theater shows and plays
- `CONFERENCE` - Professional conferences
- `FESTIVAL` - Music and cultural festivals

## AI Features Explained

### Natural Language Search
Ask questions in plain English:
- "Show me all rock concerts"
- "What events are happening at Madison Square Garden?"
- "Find me sports events in the next 30 days"

### Smart Booking
Book tickets conversationally:
- "Book 2 tickets for John Smith at john@email.com"
- "I need 3 tickets for the concert"

### Personalized Recommendations
Get suggestions based on preferences:
- "I like classical music and small venues"
- "Recommend events for someone who loves comedy"

## Database Access

H2 Console is available at: `http://localhost:8080/h2-console`

- JDBC URL: `jdbc:h2:mem:ticketdb`
- Username: `sa`
- Password: (leave empty)

## Troubleshooting

### OpenAI API Key Issues
- Ensure your API key is valid and has sufficient credits
- Check that the key is properly set in application.properties or environment variables

### Database Issues
- The H2 database is in-memory and resets on application restart
- Sample data is automatically loaded on startup

### Port Already in Use
If port 8080 is busy, change it in application.properties:
```properties
server.port=8081
```

## Architecture

```
┌─────────────────┐
│   Controllers   │ (REST API & AI Endpoints)
└────────┬────────┘
         │
┌────────▼────────┐
│    Services     │ (Business Logic)
│                 │
│ - BookingService│
│ - EventService  │
│ - AIBooking     │
│   Service       │
└────────┬────────┘
         │
┌────────▼────────┐
│  Langchain4j    │ (AI Integration)
│   Assistant     │
└────────┬────────┘
         │
┌────────▼────────┐
│   Repositories  │ (Data Access)
└────────┬────────┘
         │
┌────────▼────────┐
│   H2 Database   │
└─────────────────┘
```

## Future Enhancements

- [ ] Payment integration
- [ ] Email notifications
- [ ] Seat selection
- [ ] Multi-language support
- [ ] Mobile app integration
- [ ] Advanced analytics dashboard
- [ ] Social media integration

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License.

## Support

For issues and questions:
- Create an issue on GitHub
- Contact: support@ticketbooking.com

## Acknowledgments

- Spring Boot team for the excellent framework
- Langchain4j for AI integration capabilities
- OpenAI for GPT-4 API
- All contributors and users of this project

---

**Happy Booking! 🎫✨**
