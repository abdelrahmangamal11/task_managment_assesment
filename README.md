# Task Management API

A Spring Boot REST API for managing tasks with JWT-based authentication. This application allows users to register, login, and manage their personal tasks with a clean and secure architecture.

##  Features

- **User Authentication & Authorization**
  - User registration with email validation
  - JWT-based login/logout
  - Password encryption using BCrypt
  - Token blacklisting for secure logout

- **Task Management**
  - Create, read, update, and delete tasks
  - Task status management (Open/Done)
  - User-specific task isolation
  - Task validation and error handling

- **Security**
  - JWT token authentication
  - Password encryption
  - CORS configuration
  - Custom security exception handling

- **Database**
  - H2 in-memory database for development
  - JPA/Hibernate for ORM
  - UUID primary keys
  - Entity relationships

##  Tech Stack

- **Backend**: Spring Boot 3.5.5
- **Java**: Java 21
- **Security**: Spring Security with JWT
- **Database**: H2 (in-memory)
- **ORM**: Spring Data JPA / Hibernate
- **Build Tool**: Maven
- **Lombok**: For reducing boilerplate code
- **Testing**: JUnit 5, Mockito

##  Prerequisites

- Java 21 or higher
- Maven 3.6 or higher
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

##  Getting Started

### 1. Clone the Repository
```bash
git clone <repository-url>
cd task_project
```

### 2. Build the Project
```bash
./mvnw clean compile
```

### 3. Run the Application
```bash
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`

### 4. Access H2 Database Console (Optional)
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `task-project`
- Password: `password123`

##  API Documentation

### Authentication Endpoints

#### Register User
```http
POST /auth/register
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123",
  "name": "John Doe"
}
```

**Response:**
```json
{
  "email": "user@example.com",
  "password": "encrypted_password",
  "name": "John Doe"
}
```

#### Login
```http
POST /auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "expireIn": 86400
}
```

#### Logout
```http
POST /auth/logout
Authorization: Bearer <token>
```

#### Get All Users (Admin)
```http
GET /auth
Authorization: Bearer <token>
```

### Task Endpoints

All task endpoints require authentication. Include the JWT token in the Authorization header.

#### Get All Tasks
```http
GET /tasks
Authorization: Bearer <token>
```

**Response:**
```json
[
  {
    "id": "123e4567-e89b-12d3-a456-426614174000",
    "title": "Complete project documentation",
    "description": "Write comprehensive README and API docs",
    "status": "Open"
  }
]
```

#### Get Task by ID
```http
GET /tasks/{id}
Authorization: Bearer <token>
```

#### Create Task
```http
POST /tasks
Authorization: Bearer <token>
Content-Type: application/json

{
  "title": "New Task",
  "description": "Task description",
  "status": "Open"
}
```

#### Update Task
```http
PUT /tasks/{id}
Authorization: Bearer <token>
Content-Type: application/json

{
  "title": "Updated Task",
  "description": "Updated description",
  "status": "Done"
}
```

#### Delete Task
```http
DELETE /tasks/{id}
Authorization: Bearer <token>
```

##  Database Schema

### Users Table
| Column | Type | Constraints |
|--------|------|-------------|
| id | UUID | Primary Key |
| email | VARCHAR | Unique, Not Null |
| password | VARCHAR | Not Null |
| name | VARCHAR | Not Null |

### Tasks Table
| Column | Type | Constraints |
|--------|------|-------------|
| id | UUID | Primary Key |
| title | VARCHAR | Not Null |
| description | VARCHAR | Nullable |
| status | ENUM | Not Null (Open/Done) |
| user_id | UUID | Foreign Key to Users |

##  Configuration

### Application Properties
```properties
# Application
spring.application.name=task_project

# JWT Configuration
jwt.secret=MySuperSecretKeyMySuperSecretKeyMySuperSecretKey1234567890

# Database Configuration
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=task-project
spring.datasource.password=password123
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

##  Testing

### Run Tests
```bash
# Run all tests
./mvnw test

# Run specific test class
./mvnw test -Dtest=UserTest

# Run tests with coverage
./mvnw test jacoco:report
```

### Test Coverage
The project includes comprehensive unit tests for:
- Entity classes (User, Task)
- Service layer (AuthService, TaskService)
- Mappers (UserMappers, TaskMappers)
- Controllers (TaskController)

##  Project Structure

```
src/
 main/
    java/com/assesment/task_project/
       config/                 # Configuration classes
       controllers/            # REST controllers
       customException/        # Custom exception handlers
       domain/
          Dto/               # Data Transfer Objects
          entities/          # JPA entities
       mappers/               # Entity-DTO mappers
       repository/            # Data access layer
       security/              # Security configuration
       services/              # Business logic layer
    resources/
        application.properties # Application configuration
 test/                          # Test classes
```

##  Security Features

- **JWT Authentication**: Secure token-based authentication
- **Password Encryption**: BCrypt password hashing
- **Token Blacklisting**: Secure logout with token invalidation
- **CORS Configuration**: Cross-origin resource sharing setup
- **Custom Security Handlers**: Centralized error handling for security exceptions

##  Deployment

### Build for Production
```bash
./mvnw clean package
```

### Run JAR
```bash
java -jar target/task_project-0.0.1-SNAPSHOT.jar
```

##  Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

##  License

This project is licensed under the MIT License - see the LICENSE file for details.

##  Author

**Assessment Project**
- Spring Boot Task Management API
- Built with  using Spring Boot and Java 21

##  Known Issues

- H2 database is in-memory and data is lost on restart
- JWT secret is hardcoded (should be externalized for production)

##  Future Enhancements

- [ ] Add task categories/tags
- [ ] Implement task due dates
- [ ] Add task priority levels
- [ ] Implement task sharing between users
- [ ] Add task search and filtering
- [ ] Implement task notifications
- [ ] Add task file attachments
- [ ] Implement task comments/notes

---

**Happy Coding! **

