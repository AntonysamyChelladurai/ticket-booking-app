# Project Structure

```
ticket-booking-app/
│
├── pom.xml                                    # Maven configuration
├── .gitignore                                 # Git ignore rules
├── README.md                                  # Main documentation
├── QUICKSTART.md                              # Quick start guide
├── API_EXAMPLES.md                            # API usage examples
├── ARCHITECTURE.md                            # Architecture overview
│
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/
    │   │       └── ticketbooking/
    │   │           │
    │   │           ├── TicketBookingAiApplication.java
    │   │           │   └── Main Spring Boot application class
    │   │           │
    │   │           ├── config/
    │   │           │   ├── LangChain4jConfig.java
    │   │           │   │   └── Langchain4j configuration
    │   │           │   │   └── OpenAI model setup
    │   │           │   │   └── AI assistant bean creation
    │   │           │   │
    │   │           │   └── DataInitializer.java
    │   │           │       └── Sample event data loader
    │   │           │       └── Runs on application startup
    │   │           │
    │   │           ├── controller/
    │   │           │   ├── EventController.java
    │   │           │   │   └── REST endpoints for events
    │   │           │   │   └── CRUD operations
    │   │           │   │   └── Search and filter endpoints
    │   │           │   │
    │   │           │   ├── BookingController.java
    │   │           │   │   └── REST endpoints for bookings
    │   │           │   │   └── Create, retrieve, cancel bookings
    │   │           │   │
    │   │           │   └── AIController.java
    │   │           │       └── AI-powered endpoints
    │   │           │       └── Natural language chat
    │   │           │       └── AI booking and recommendations
    │   │           │
    │   │           ├── dto/
    │   │           │   ├── BookingRequest.java
    │   │           │   │   └── Request DTO for bookings
    │   │           │   │   └── Input validation
    │   │           │   │
    │   │           │   └── BookingResponse.java
    │   │           │       └── Response DTO for bookings
    │   │           │       └── Formatted booking details
    │   │           │
    │   │           ├── entity/
    │   │           │   ├── Event.java
    │   │           │   │   └── JPA entity for events
    │   │           │   │   └── Database table: events
    │   │           │   │
    │   │           │   └── Booking.java
    │   │           │       └── JPA entity for bookings
    │   │           │       └── Database table: bookings
    │   │           │       └── Enum: BookingStatus
    │   │           │
    │   │           ├── repository/
    │   │           │   ├── EventRepository.java
    │   │           │   │   └── JPA repository for events
    │   │           │   │   └── Custom query methods
    │   │           │   │
    │   │           │   └── BookingRepository.java
    │   │           │       └── JPA repository for bookings
    │   │           │       └── Query by email, reference, status
    │   │           │
    │   │           └── service/
    │   │               ├── EventService.java
    │   │               │   └── Event business logic
    │   │               │   └── Event search and filtering
    │   │               │
    │   │               ├── BookingService.java
    │   │               │   └── Booking business logic
    │   │               │   └── Seat management
    │   │               │   └── Booking reference generation
    │   │               │
    │   │               ├── AIBookingService.java
    │   │               │   └── AI integration service
    │   │               │   └── Natural language processing
    │   │               │   └── Query analysis and extraction
    │   │               │
    │   │               └── TicketBookingAssistant.java
    │   │                   └── Langchain4j AI interface
    │   │                   └── System prompts
    │   │                   └── AI method definitions
    │   │
    │   └── resources/
    │       └── application.properties
    │           └── Application configuration
    │           └── Database settings
    │           └── AI model configuration
    │
    └── test/
        └── java/
            └── (Test classes would go here)
```

## File Descriptions

### Root Level Files

#### pom.xml
- Maven build configuration
- Dependencies: Spring Boot, Spring AI, Langchain4j, H2, Lombok
- Build plugins and repositories
- Java version: 17

#### .gitignore
- Excludes compiled files, IDE settings, logs
- Ignores target/ directory
- Prevents committing sensitive config

#### README.md
- Complete project documentation
- Setup instructions
- API documentation
- Features overview
- Architecture diagram

#### QUICKSTART.md
- 5-minute quick start guide
- Essential setup steps
- Basic testing commands
- Troubleshooting tips

#### API_EXAMPLES.md
- Comprehensive API examples
- curl commands for all endpoints
- Usage scenarios
- Expected responses

#### ARCHITECTURE.md
- Detailed architecture overview
- Component descriptions
- Data flow diagrams
- Technology integration

### Java Source Files

#### Main Application

**TicketBookingAiApplication.java**
- Entry point of the application
- `@SpringBootApplication` annotation
- Enables auto-configuration
- Component scanning

#### Configuration Package

**LangChain4jConfig.java**
- Spring configuration for Langchain4j
- Creates `ChatLanguageModel` bean (OpenAI)
- Creates `TicketBookingAssistant` bean
- Configures chat memory
- Sets model parameters (temperature, timeout)

**DataInitializer.java**
- Implements `CommandLineRunner`
- Runs on application startup
- Loads 8 sample events
- Different categories: CONCERT, SPORTS, THEATER, etc.

#### Controller Package

**EventController.java**
- `@RestController` for event endpoints
- Base path: `/api/events`
- Methods:
  - GET all events
  - GET by ID
  - POST create event
  - PUT update event
  - DELETE event
  - GET search by name/category/venue
  - GET upcoming/available events

**BookingController.java**
- `@RestController` for booking endpoints
- Base path: `/api/bookings`
- Methods:
  - POST create booking
  - GET by reference
  - GET by email
  - DELETE cancel booking
- Input validation with `@Valid`

**AIController.java**
- `@RestController` for AI endpoints
- Base path: `/api/ai`
- Methods:
  - POST `/chat` - Natural language interaction
  - POST `/book/{eventId}` - AI-powered booking
  - POST `/recommendations` - Personalized suggestions

#### DTO Package

**BookingRequest.java**
- Data transfer object for booking input
- Fields: eventId, customerName, customerEmail, numberOfTickets
- Bean validation annotations:
  - `@NotNull`, `@NotBlank`, `@Email`
  - `@Min`, `@Max` for ticket count

**BookingResponse.java**
- Data transfer object for booking output
- Complete booking details
- Event information included
- Status and confirmation message

#### Entity Package

**Event.java**
- JPA entity for events
- Table: `events`
- Fields:
  - Basic info: id, name, venue, description
  - Timing: eventDate
  - Pricing: ticketPrice
  - Capacity: availableSeats, totalSeats
  - Categorization: category
- Lombok annotations for getters/setters

**Booking.java**
- JPA entity for bookings
- Table: `bookings`
- Fields:
  - Reference: id, bookingReference
  - Relationship: event (ManyToOne)
  - Customer: customerName, customerEmail
  - Details: numberOfTickets, totalAmount
  - Tracking: bookingDate, status
- Enum: BookingStatus (CONFIRMED, PENDING, CANCELLED)

#### Repository Package

**EventRepository.java**
- Extends `JpaRepository<Event, Long>`
- Custom query methods:
  - `findByCategory(String category)`
  - `findByEventDateBetween(...)`
  - `findByVenueContainingIgnoreCase(...)`
  - `findByNameContainingIgnoreCase(...)`
  - `findByAvailableSeatsGreaterThan(...)`

**BookingRepository.java**
- Extends `JpaRepository<Booking, Long>`
- Custom query methods:
  - `findByCustomerEmail(String email)`
  - `findByBookingReference(String reference)`
  - `findByEventId(Long eventId)`
  - `findByStatus(BookingStatus status)`

#### Service Package

**EventService.java**
- Event management business logic
- Methods:
  - CRUD operations
  - Search by name/category/venue
  - Get upcoming events
  - Get available events
- Seat initialization on creation

**BookingService.java**
- Booking management business logic
- Methods:
  - Create booking (with validation)
  - Get booking by reference/email
  - Cancel booking (restore seats)
- Transactional operations
- Booking reference generation (UUID-based)

**AIBookingService.java**
- AI integration layer
- Methods:
  - `processNaturalLanguageQuery()` - Search via NL
  - `processNaturalLanguageBooking()` - Book via NL
  - `getEventRecommendations()` - AI suggestions
- Uses TicketBookingAssistant interface
- JSON parsing for extracted info
- Event formatting for AI context

**TicketBookingAssistant.java**
- Langchain4j AI service interface
- Auto-implemented by Langchain4j
- Methods with `@SystemMessage` and `@UserMessage`:
  - `chat()` - General conversation
  - `extractBookingInfo()` - Extract from NL
  - `analyzeSearchQuery()` - Determine search intent
- System prompts define AI behavior

### Resources

**application.properties**
- Server configuration (port: 8080)
- H2 database configuration (in-memory)
- JPA/Hibernate settings
- Spring AI configuration:
  - OpenAI API key
  - Model: gpt-4
  - Temperature: 0.7
- Langchain4j configuration
- Logging levels

## Key Design Patterns

### 1. Repository Pattern
- Abstraction for data access
- Spring Data JPA repositories
- Custom query methods

### 2. Service Layer Pattern
- Business logic separation
- Transaction management
- Reusable components

### 3. DTO Pattern
- Data transfer objects
- Request/response separation
- Input validation

### 4. Dependency Injection
- Spring's IoC container
- Constructor injection
- `@RequiredArgsConstructor` (Lombok)

### 5. Interface-based AI Integration
- Langchain4j service interfaces
- Declarative AI methods
- Automatic implementation

## Dependencies Summary

### Spring Boot
- `spring-boot-starter-web` - REST API
- `spring-boot-starter-data-jpa` - Database
- `spring-boot-starter-validation` - Input validation

### Spring AI
- `spring-ai-openai-spring-boot-starter` - OpenAI integration

### Langchain4j
- `langchain4j` - Core library
- `langchain4j-open-ai` - OpenAI provider
- `langchain4j-spring-boot-starter` - Spring integration

### Database
- `h2` - In-memory database

### Utilities
- `lombok` - Boilerplate reduction

## Development Workflow

1. **Start**: Run `TicketBookingAiApplication`
2. **Initialize**: `DataInitializer` loads sample data
3. **Ready**: API endpoints available
4. **Request**: Controller receives HTTP request
5. **Process**: Service layer handles business logic
6. **AI** (if needed): Langchain4j processes NL
7. **Data**: Repository accesses database
8. **Response**: JSON returned to client

## Testing Strategy

### Manual Testing
- Use curl commands from API_EXAMPLES.md
- Test via Postman
- Access H2 console for data verification

### Recommended Unit Tests
- Service layer methods
- Repository queries
- DTO validation
- AI extraction logic

### Integration Tests
- End-to-end booking flow
- AI conversation flows
- Database transactions

---

This structure provides clear separation of concerns, making the application maintainable and scalable.
