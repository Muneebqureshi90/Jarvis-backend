# Jarvis Backend - User CRUD API

Spring Boot REST API for User management with full CRUD operations.

## Features

- Create, Read, Update, Delete (CRUD) operations for User entity
- Request validation with proper error responses
- BCrypt password encoding
- Optimistic locking with @Version
- H2 in-memory database (easy to switch to MySQL)
- OpenAPI/Swagger UI documentation
- Global exception handling
- Database indexes on frequently queried columns (email, phone)

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET    | `/api/users` | Retrieve all users |
| GET    | `/api/users/{id}` | Retrieve a specific user |
| POST   | `/api/users` | Create a new user |
| PUT    | `/api/users/{id}` | Update an existing user |
| DELETE | `/api/users/{id}` | Delete a user |
| GET    | `/api/users/search?query={query}` | Search users by name |
| GET    | `/api/users/created-after/{date}` | Get users created after date |

## Running the Application

1. Build the project:
   ```bash
   mvn clean package
   ```

2. Run the JAR:
   ```bash
   java -jar target/jarvis-backend-0.0.1-SNAPSHOT.jar
   ```

3. Or run from IDE (Spring Boot main class).

The API will be available at: `http://localhost:8080/api`

## Swagger UI

Access interactive API documentation at: `http://localhost:8080/api/swagger-ui.html`

## H2 Console

Database console available at: `http://localhost:8080/api/h2-console`

- Driver Class: `org.h2.Driver`
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (empty)

## Project Structure

```
src/main/java/com/example/jarvisbackend/
├── JarvisBackendApplication.java  # Main application class
├── config/                         # Configuration classes
├── controller/                     # REST controllers
│   └── UserController.java
├── dto/                            # Data transfer objects
│   ├── UserRequest.java
│   └── UserResponse.java
├── entity/                         # JPA entities
│   └── User.java
├── exception/                      # Custom exceptions
│   ├── DuplicateResourceException.java
│   └── ResourceNotFoundException.java
├── handler/                        # Exception handlers
│   ├── GlobalExceptionHandler.java
│   ├── ErrorResponse.java
│   └── ValidationErrorResponse.java
└── service/                        # Business logic
    └── UserService.java
```

## Database Schema

The application uses Hibernate's `ddl-auto=update` to auto-create/update the schema.

```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(60) NOT NULL,
    phone VARCHAR(20),
    address VARCHAR(200),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    version INTEGER,
    INDEX idx_user_email (email),
    INDEX idx_user_phone (phone)
);
```

## Security Notes

- Passwords are hashed using BCrypt
- No hardcoded secrets (database credentials are configurable via environment variables)
- All inputs are validated using Jakarta Validation
- Optimistic locking prevents concurrent update conflicts
- For production, enable HTTPS, configure CORS, and use proper authentication/authorization

## Switching to MySQL

1. Uncomment MySQL dependency in `pom.xml`
2. Uncomment MySQL properties in `application.properties`
3. Update `spring.jpa.database-platform` to `org.hibernate.dialect.MySQL8Dialect`
4. Set `spring.jpa.hibernate.ddl-auto=validate` for production
