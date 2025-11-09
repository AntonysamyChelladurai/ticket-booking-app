# Quick Start Guide - Ticket Booking AI Application

## 🚀 Get Started in 5 Minutes

### Step 1: Prerequisites Check
Make sure you have:
- ✅ Java 17+ installed
- ✅ Maven 3.6+ installed
- ✅ OpenAI API Key (get one at https://platform.openai.com/api-keys)

### Step 2: Setup
```bash
# Navigate to project directory
cd ticket-booking-app

# Set your OpenAI API key (choose one method)

# Method 1: Environment variable (recommended)
export OPENAI_API_KEY=your-api-key-here

# Method 2: Or edit src/main/resources/application.properties
# Replace 'your-api-key-here' with your actual key
```

### Step 3: Build & Run
```bash
# Build the application
mvn clean install

# Run the application
mvn spring-boot:run
```

Wait for: `Started TicketBookingAiApplication in X seconds`

### Step 4: Test the Application

**Option 1: Simple Test (Traditional API)**
```bash
# Get all events
curl http://localhost:8080/api/events
```

**Option 2: AI Test (Natural Language)**
```bash
# Chat with AI
curl -X POST http://localhost:8080/api/ai/chat \
  -H "Content-Type: application/json" \
  -d '{"message": "What concerts are available?"}'
```

**Option 3: Make a Booking**
```bash
# Book tickets
curl -X POST http://localhost:8080/api/bookings \
  -H "Content-Type: application/json" \
  -d '{
    "eventId": 1,
    "customerName": "John Doe",
    "customerEmail": "john@example.com",
    "numberOfTickets": 2
  }'
```

### Step 5: Explore More

🌐 **Access H2 Database Console:**
- URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:ticketdb`
- Username: `sa`
- Password: (leave empty)

📚 **Check Available Events:**
- The app comes with 8 pre-loaded sample events
- Events include concerts, sports, theater, and more!

🤖 **Try AI Features:**
- Natural language search: "Show me all sports events"
- AI booking: "Book 2 tickets for Jane, email jane@test.com"
- Recommendations: "Suggest events for someone who likes rock music"

### Common Commands

**Stop the application:**
Press `Ctrl + C` in the terminal

**View logs:**
Logs are displayed in the terminal where you ran `mvn spring-boot:run`

**Rebuild after changes:**
```bash
mvn clean install
mvn spring-boot:run
```

## 📖 Full Documentation

- **README.md** - Complete documentation
- **API_EXAMPLES.md** - Detailed API examples and use cases

## ⚠️ Troubleshooting

**Issue: Port 8080 already in use**
- Solution: Change port in `src/main/resources/application.properties`
  ```properties
  server.port=8081
  ```

**Issue: OpenAI API errors**
- Check your API key is valid
- Ensure you have credits in your OpenAI account
- Verify the key is properly set in environment variable or properties file

**Issue: Maven build fails**
- Ensure Java 17+ is installed: `java -version`
- Ensure Maven is installed: `mvn -version`
- Clear Maven cache: `mvn clean`

## 🎯 What to Try Next

1. **Create your own event** using POST /api/events
2. **Book tickets** using natural language AI
3. **Get recommendations** based on your preferences
4. **Explore the H2 database** to see how data is stored
5. **Check the code** to understand how Langchain4j integrates with Spring

## 🆘 Need Help?

- Check the full **README.md** for detailed information
- Review **API_EXAMPLES.md** for more request examples
- Look at the code comments for implementation details

---

**Enjoy your AI-powered ticket booking system! 🎫✨**
