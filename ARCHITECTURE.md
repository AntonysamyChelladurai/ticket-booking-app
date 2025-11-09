# Architecture Overview - Ticket Booking AI Application

## System Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                         Client Layer                             │
│  (Web Browser, Mobile App, API Client, Postman, curl)           │
└────────────────────────────┬────────────────────────────────────┘
                             │ HTTP/REST
                             │
┌────────────────────────────▼────────────────────────────────────┐
│                     REST Controllers Layer                       │
│  ┌─────────────┐  ┌─────────────┐  ┌──────────────────┐        │
│  │   Event     │  │   Booking   │  │   AI Controller  │        │
│  │ Controller  │  │ Controller  │  │  (Langchain4j)   │        │
│  └─────────────┘  └─────────────┘  └──────────────────┘        │
└────────────────────────────┬────────────────────────────────────┘
                             │
┌────────────────────────────▼────────────────────────────────────┐
│                      Service Layer                               │
│  ┌─────────────┐  ┌─────────────┐  ┌──────────────────┐        │
│  │   Event     │  │   Booking   │  │   AI Booking     │        │
│  │  Service    │  │   Service   │  │    Service       │        │
│  └─────────────┘  └─────────────┘  └──────────────────┘        │
│                                                                  │
│  ┌──────────────────────────────────────────────────┐           │
│  │         TicketBookingAssistant Interface         │           │
│  │        (Langchain4j AI Service Interface)        │           │
│  └──────────────────────────────────────────────────┘           │
└────────────────────────────┬────────────────────────────────────┘
                             │
            ┌────────────────┴────────────────┐
            │                                 │
┌───────────▼──────────┐           ┌─────────▼──────────┐
│  Repository Layer    │           │   Langchain4j      │
│  ┌────────────────┐  │           │   Integration      │
│  │ Event Repo     │  │           │                    │
│  │ Booking Repo   │  │           │  ┌──────────────┐  │
│  └────────────────┘  │           │  │ ChatLanguage │  │
└──────────┬───────────┘           │  │    Model     │  │
           │                       │  └──────┬───────┘  │
           │                       └─────────┼──────────┘
           │                                 │
┌──────────▼─────────────────────────────────▼──────────┐
│              External Services Layer                   │
│  ┌──────────────┐              ┌──────────────┐       │
│  │ H2 Database  │              │  OpenAI API  │       │
│  │  (In-Memory) │              │    (GPT-4)   │       │
│  └──────────────┘              └──────────────┘       │
└────────────────────────────────────────────────────────┘
```

## Component Details

### 1. Controller Layer

#### EventController
- Handles CRUD operations for events
- Endpoints for searching and filtering events
- Supports queries by name, category, venue, date

#### BookingController
- Manages ticket bookings
- Handles booking creation, retrieval, and cancellation
- Validates booking requests

#### AIController
- Provides natural language interfaces
- `/api/ai/chat` - Conversational AI assistant
- `/api/ai/book/{eventId}` - Natural language booking
- `/api/ai/recommendations` - AI-powered recommendations

### 2. Service Layer

#### EventService
- Business logic for event management
- Event search and filtering
- Event availability checks

#### BookingService
- Booking creation and validation
- Seat availability management
- Booking reference generation
- Booking cancellation with seat restoration

#### AIBookingService
- Integrates Langchain4j with booking logic
- Natural language query processing
- Information extraction from user messages
- Event recommendation engine

#### TicketBookingAssistant (Interface)
- Langchain4j AI service interface
- Defines AI assistant behaviors
- System messages for context
- Methods for chat, extraction, and analysis

### 3. Repository Layer

#### EventRepository
- JPA repository for Event entities
- Custom queries for event search
- Category and venue filtering

#### BookingRepository
- JPA repository for Booking entities
- Queries by customer email
- Booking reference lookups

### 4. Entity Layer

#### Event
- id, name, venue, eventDate
- ticketPrice, availableSeats, totalSeats
- description, category

#### Booking
- id, event (reference)
- customerName, customerEmail
- numberOfTickets, totalAmount
- bookingDate, status, bookingReference

### 5. Configuration Layer

#### LangChain4jConfig
- Configures ChatLanguageModel (OpenAI)
- Sets up TicketBookingAssistant bean
- Manages chat memory
- API key and model configuration

#### DataInitializer
- Loads sample events on startup
- Demonstrates different event categories
- Provides test data

## Data Flow

### Traditional Booking Flow
```
User Request → BookingController → BookingService 
    → EventRepository (check seats)
    → BookingRepository (save booking)
    → EventRepository (update seats)
    → Return BookingResponse
```

### AI-Powered Search Flow
```
User Query → AIController → AIBookingService
    → TicketBookingAssistant (analyze query)
    → OpenAI API (process natural language)
    → EventService (search events)
    → TicketBookingAssistant (generate response)
    → Return AI Response
```

### AI-Powered Booking Flow
```
User Message → AIController → AIBookingService
    → TicketBookingAssistant (extract info)
    → OpenAI API (parse booking details)
    → BookingService (create booking)
    → Return BookingResponse
```

## Technology Stack Integration

### Spring Boot Framework
- Core application framework
- Dependency injection
- Auto-configuration
- REST API support

### Spring Data JPA
- Repository abstraction
- Database operations
- Query methods

### Spring AI
- AI integration framework
- OpenAI client support
- Prompt templates

### Langchain4j
- LLM integration
- AI service interfaces
- Chat memory management
- Prompt engineering

### H2 Database
- In-memory database
- Development and testing
- SQL console access

### OpenAI GPT-4
- Natural language processing
- Intent recognition
- Information extraction
- Response generation

## Key Features Implementation

### Natural Language Understanding
- User queries processed by GPT-4
- Intent classification (search, book, recommend)
- Entity extraction (event name, dates, preferences)

### Smart Search
- AI analyzes search intent
- Determines search type (name, category, venue, date)
- Routes to appropriate repository method

### Conversational Booking
- Extracts customer details from natural language
- Validates required information
- Creates structured booking requests

### Recommendations
- Analyzes user preferences
- Matches with available events
- Provides personalized suggestions

## Security Considerations

### Current Implementation
- API key stored in properties (development)
- Basic validation on inputs
- In-memory database (no persistence)

### Production Recommendations
- Use environment variables for secrets
- Implement authentication/authorization
- Add rate limiting
- Use production database (PostgreSQL, MySQL)
- HTTPS encryption
- Input sanitization
- CORS configuration

## Scalability Considerations

### Current Design
- Single instance application
- In-memory database
- Synchronous processing

### Scale-Up Options
- Add caching (Redis)
- Database connection pooling
- Async processing for AI calls
- Message queues for bookings
- Load balancing
- Microservices architecture
- Event-driven architecture

## Monitoring & Observability

### Logging
- SLF4J with Logback
- Request/response logging
- Error tracking

### Recommended Additions
- Spring Boot Actuator
- Metrics (Micrometer)
- Distributed tracing
- Health checks
- Performance monitoring

## API Design Patterns

### RESTful Principles
- Resource-based URLs
- HTTP methods (GET, POST, DELETE)
- Status codes
- JSON responses

### AI Integration Pattern
- Separate AI endpoints
- Clear distinction between traditional and AI features
- Fallback mechanisms
- Error handling

---

This architecture provides a solid foundation for an AI-powered ticket booking system while maintaining clean separation of concerns and scalability potential.
