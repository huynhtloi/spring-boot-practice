# Spring Boot Training Project

A comprehensive Spring Boot application demonstrating core concepts, REST APIs, database integration, and best practices.

## 🎯 Project Overview

This project serves as a hands-on learning experience for Spring Boot fundamentals, covering everything from basic setup to advanced features like MapStruct mapping, JPA auditing, and API design patterns.

## 📋 Checklist Progress

### ✅ Spring Boot Project Setup
- [x] **Finish setting up a new Spring Boot Project** 
  - Created using [Spring Initializr](https://start.spring.io/)
  - Dependencies: Spring Data JPA, Lombok, MySQL Driver, Spring Security, Spring Web

### ✅ Spring Core Foundations
- [x] **Create a simple "Hello API" Spring Boot app**
- [x] **Using Lombok** - `@Data`, `@Builder`, `@RequiredArgsConstructor`
- [x] **Practice DI with services and interfaces** - Service layer with dependency injection
- [x] **Read and modify application.properties or YAML config** - External configuration management

### ✅ Building REST APIs (CRUD)
- [x] **Build a CRUD API** using:
  - `@RestController`, `@RequestMapping`
  - `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`
  - `@RequestParam`, `@PathVariable`, `@RequestBody`, `@RequestHeader`, `@ResponseBody`
- [x] **Use Postman to test** headers, params, and body
- [x] **API versioning** with path-based versioning

### ✅ Error Handling, Validation and Logging
- [x] **Throw custom exceptions** from the service layer
- [x] **Catch them globally** with `@ControllerAdvice` and `@ExceptionHandler`
- [x] **Return proper HTTP status codes**
- [x] **Add SLF4J/Logback Dependency**
- [x] **Write logs during development**

### ✅ Configuration Management
- [x] **Create config classes bound to application.yml**
- [x] **Inject them into services** using `@Autowired`

### 🔄 REST Clients (In Progress)
- [ ] **Create @HttpExchange interfaces** for 3rd-party APIs
- [ ] **REST Client**: `@GetExchange`, `@PostExchange`, `@PutExchange`, `@PatchExchange`, `@DeleteExchange`

### ✅ MapStruct Integration
- [x] **Map between Entity ↔ DTO with MapStruct**
- [x] **Replace manual mapping code** with MapStruct
- [x] **Use @AfterMapping** for logic like setting computed fields

### 🔄 Testing Layer (Planned)
- [ ] **Write unit tests** for services with JUnit & Mockito
- [ ] **Use @WebMvcTest** for the controller layer
- [ ] **Add integration test** with `@SpringBootTest`
- [ ] **Use WireMock** to stub external APIs

### 🔄 Firebase Integration (Planned)
- [ ] **Create a user** via the Firebase emulator
- [ ] **User login** implementation

### ✅ Working with Databases
- [x] **Add a MySQL** database
- [x] **Create Entity, Repository, and integrate with Controller**
- [x] **Replace manual mapping code with MapStruct**
- [x] **Map between Entity ↔ DTO**
- [x] **Use @JsonIgnoreProperties** to prevent circular references or hide fields

## 🛠️ Tech Stack

- **Framework**: Spring Boot 3.5.3
- **Language**: Java 17
- **Database**: MySQL 8.4.5
- **Mapping**: MapStruct 1.5.5.Final
- **Build Tool**: Maven
- **Logging**: SLF4J + Logback
- **Utilities**: Lombok

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/training/practice/
│   │   ├── config/           # Configuration classes
│   │   ├── controller/       # REST Controllers
│   │   ├── dto/              # Data Transfer Objects
│   │   ├── entity/           # JPA Entities
│   │   ├── mapper/           # MapStruct Mappers
│   │   ├── repository/       # JPA Repositories
│   │   ├── service/          # Business Logic Services
│   │   └── PracticeApplication.java
│   └── resources/
│       ├── application.yml   # Configuration
│       └── static/
└── test/                     # Test cases (planned)
```

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Maven 3.6+
- MySQL 8.0+

### Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd java-training/practice
   ```

2. **Configure database**
   Update `application.yml` with your MySQL credentials:
   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/practice?useSSL=false&allowPublicKeyRetrieval=true
       username: your_username
       password: your_password
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the API**
   - Base URL: `http://localhost:8888`
   - API Documentation: Available via controller endpoints

## 🔗 API Endpoints

### User Management
- `GET /api/users` - Get all users (with pagination)
- `GET /api/users/{id}` - Get user by ID
- `POST /api/users` - Create new user
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user
- `PATCH /api/users/{id}/subject` - Add subject to user

### Subject Management
- `GET /api/subjects` - Get all subjects (with pagination)
- `GET /api/subjects/{id}` - Get subject by ID
- `GET /api/subjects/search?q={query}` - Search subjects
- `PUT /api/subjects/{id}` - Update subject

## 🗄️ Database Schema

### Users Table
```sql
CREATE TABLE users (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20),
    department VARCHAR(100),
    status ENUM('ACTIVE', 'INACTIVE', 'SUSPENDED'),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

### Subjects Table
```sql
CREATE TABLE subjects (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(50),
    code VARCHAR(3) NOT NULL,
    user_id VARCHAR(36),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

## 🎯 Key Features Implemented

### 1. **MapStruct Integration**
- Automatic Entity ↔ DTO mapping
- Null value handling strategies
- Nested object mapping (User → Subjects)
- Custom mapping configurations

### 2. **JPA Auditing**
- Automatic timestamp management with `@CreationTimestamp` and `@UpdateTimestamp`
- Enabled with `@EnableJpaAuditing`

### 3. **JSON Serialization Control**
- `@JsonIgnoreProperties` to prevent circular references
- Clean API responses without unwanted fields

### 4. **Configuration Management**
- External configuration with `@ConfigurationProperties`
- Environment-specific settings
- Feature toggles and pagination controls

### 5. **Error Handling**
- Global exception handling
- Proper HTTP status codes
- Structured error responses

### 6. **Logging**
- Comprehensive logging throughout the application
- Request/response tracking with request IDs

## 🧪 Testing

### Manual Testing with Postman
```bash
# Get user with subjects
curl --location 'http://localhost:8888/api/users/3e0015bc-2060-46c7-a76f-00e55e0b56fb' \
--header 'X-Request-ID: get-user-1753621943'

# Add subject to user
curl --location --request PATCH 'http://localhost:8888/api/users/3e0015bc-2060-46c7-a76f-00e55e0b56fb/subject' \
--header 'Content-Type: application/json' \
--header 'X-Request-ID: test-add-subject-002' \
--data '{
    "name": "Computer Science",
    "description": "Introduction to programming and algorithms",
    "code": "CS1"
}'
```

## 🔄 Recent Improvements

- ✅ **MapStruct Implementation**: Replaced manual mapping with MapStruct
- ✅ **Timestamp Auditing**: Implemented JPA auditing for automatic timestamps
- ✅ **Circular Reference Prevention**: Used `@JsonIgnoreProperties` strategically
- ✅ **Clean API Responses**: Removed unwanted timestamp fields from Subject responses

## 🎯 Next Steps

1. **Testing Implementation**
   - Unit tests with JUnit & Mockito
   - Integration tests with `@SpringBootTest`
   - Controller tests with `@WebMvcTest`

2. **REST Client Integration**
   - Implement `@HttpExchange` interfaces
   - External API integration

3. **Firebase Integration**
   - User authentication
   - Firebase emulator setup

4. **Advanced Features**
   - API documentation with OpenAPI/Swagger
   - Caching implementation
   - Security enhancements

## 📚 Learning Outcomes

This project demonstrates mastery of:
- Spring Boot fundamentals and dependency injection
- REST API design and implementation
- Database integration with JPA/Hibernate
- MapStruct for object mapping
- Configuration management
- Error handling and validation
- Logging best practices
- JSON serialization control

---

**Built with ❤️ for learning Spring Boot ecosystem**
