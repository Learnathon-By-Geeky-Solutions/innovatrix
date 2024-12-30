Here's a comprehensive documentation for the Restaurant Management System:

# Restaurant Management System Documentation

## Overview
The Restaurant Management System is a Spring Boot application that provides RESTful APIs for managing restaurant information. It uses H2 as an in-memory database and follows a standard layered architecture.

## Technical Stack
- **Framework**: Spring Boot 3.2.1
- **Database**: H2 (In-memory)
- **Build Tool**: Maven
- **Java Version**: 17

## Architecture
The application follows a layered architecture:
1. **Controller Layer**: Handles HTTP requests and responses
2. **Service Layer**: Contains business logic
3. **Repository Layer**: Manages data persistence
4. **Model Layer**: Data entities

### Components
- `Restaurant.java`: Entity class representing restaurant data
- `RestaurantRepository.java`: JPA repository for database operations
- `RestaurantService.java`: Service class containing business logic
- `RestaurantController.java`: REST controller for handling HTTP requests

## API Endpoints

### 1. Get All Restaurants
```
GET /restaurants
Response: 200 OK
[
    {
        "id": 1,
        "name": "The Great Restaurant",
        "address": "123 Main Street",
        "cuisine": "Italian"
    }
]
```

### 2. Get Restaurant by ID
```
GET /restaurants/{id}
Response: 200 OK
{
    "id": 1,
    "name": "The Great Restaurant",
    "address": "123 Main Street",
    "cuisine": "Italian"
}
```

### 3. Create Restaurant
```
POST /restaurants
Request Body:
{
    "name": "The Great Restaurant",
    "address": "123 Main Street",
    "cuisine": "Italian"
}
Response: 201 Created
```

### 4. Update Restaurant
```
PUT /restaurants/{id}
Request Body:
{
    "name": "Updated Restaurant",
    "address": "456 New Street",
    "cuisine": "French"
}
Response: 200 OK
```

### 5. Delete Restaurant
```
DELETE /restaurants/{id}
Response: 204 No Content
```

## Database Configuration
```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=password
spring.h2.console.enabled=true
```

## Setup Instructions

1. Clone the repository
```bash
git clone [repository-url]
```

2. Build the project
```bash
mvn clean install
```

3. Run the application
```bash
mvn spring:boot run
```

4. Access H2 Console
```
URL: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:testdb
Username: sa
Password: password
```

## Entity Structure
```java
Restaurant {
    Long id
    String name
    String address
    String cuisine
}
```

## Error Handling
- 404: Restaurant not found
- 400: Invalid request parameters
- 500: Internal server error

## Security Considerations
- H2 console is enabled for development
- Basic authentication can be added
- CORS configuration might be needed for frontend integration

## Future Enhancements
1. Authentication and Authorization
2. Input validation
3. Response DTOs
4. Integration tests
5. Swagger documentation
6. Logging implementation
7. Production-ready database configuration

## Development Guidelines
1. Follow RESTful conventions
2. Use proper HTTP status codes
3. Implement proper error handling
4. Add unit tests for each layer
5. Document API changes

Would you like me to elaborate on any specific section or add more details?
