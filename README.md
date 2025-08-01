# Spring Boot Training Project

A comprehensive Spring Boot application demonstrating advanced enterprise patterns, REST APIs, external system integration, and modern development practices.

## 🎯 Project Overview

This project is a complete enterprise-grade Spring Boot application showcasing advanced features including dual-entity management (Users & Subjects), external API integration via @HttpExchange, comprehensive MapStruct mapping patterns, and production-ready testing infrastructure.

## 📋 Feature Checklist Progress

### ✅ Spring Boot Project Setup
- [x] **Finish setting up a new Spring Boot Project** 
  - Created using [Spring Initializr](https://start.spring.io/)
  - Dependencies: Spring Data JPA, Lombok, MySQL Driver, Spring Security, Spring Web, Spring WebFlux

### ✅ Spring Core Foundations
- [x] **Create a simple "Hello API" Spring Boot app** - HelloController with greeting service and DependencyInjectionController for DI examples
- [x] **Using Lombok** - `@Data`, `@Builder`, `@RequiredArgsConstructor`, `@Slf4j`
- [x] **Practice DI with services and interfaces** - Comprehensive service layer architecture with multiple greeting services
- [x] **Read and modify application.properties or YAML config** - Feature toggles, pagination, HTTP client configuration

### ✅ Building Advanced REST APIs (CRUD)
- [x] **Build comprehensive CRUD APIs** using:
  - `@RestController`, `@RequestMapping` with path versioning (`/api/users`, `/api/users/v2`, `/api/subjects`, `/api/external/postman`)
  - `@GetMapping`, `@PostMapping`, `@PutMapping`, `@PatchMapping`, `@DeleteMapping`
  - `@RequestParam`, `@PathVariable`, `@RequestBody`, `@RequestHeader`, `@ResponseBody`
  - **Advanced features**: Pagination, sorting, search, status filtering, department filtering
- [x] **Use Postman to test** headers, params, and body with comprehensive test collections
- [x] **API versioning** with path-based versioning (UserController vs UserV2Controller)
- [x] **Multiple controller types**: User management, Subject management, External API integration, System APIs, Configuration management

### ✅ Error Handling, Validation and Logging
- [x] **Throw custom exceptions** from the service layer (UserNotFoundException, EmailAlreadyExistsException, SubjectNotFoundException)
- [x] **Catch them globally** with `@ControllerAdvice` and `@ExceptionHandler`
- [x] **Return proper HTTP status codes** with structured `ApiResponseDTO` responses
- [x] **Add SLF4J/Logback Dependency** with `@Slf4j` annotation
- [x] **Write comprehensive logs** with request IDs and structured logging patterns

### ✅ Configuration Management
- [x] **Create feature toggle config classes** bound to application.yml (`FeatureToggleConfig`)
- [x] **HTTP client configuration** (`PostmanClientConfig`) with WebClient
- [x] **Pagination configuration** (`PaginationConfig`) for default page sizes
- [x] **Inject them into services** using dependency injection

### ✅ REST Clients & External Integration
- [x] **Create @HttpExchange interfaces** for 3rd-party APIs (`PostmanClient`)
- [x] **Complete REST Client**: `@GetExchange`, `@PostExchange`, `@PutExchange`, `@PatchExchange`, `@DeleteExchange`
- [x] **WebClient Configuration** with proper error handling, timeouts, and base URL setup
- [x] **External API Controller** (`PostmanController`) with full CRUD operations for external systems
- [x] **Bi-directional mapping** between internal and external API formats via `PostmanClientMapper`

### ✅ MapStruct Integration (Advanced)
- [x] **Map between Entity ↔ DTO with MapStruct** (`UserMapper`, `SubjectMapper`, `PostmanClientMapper`)
- [x] **Replace manual mapping code** with MapStruct throughout the application
- [x] **Use @AfterMapping** for post-processing logic:
  - String formatting in `PostmanClientMapper.formatUserPostmanStatus()` (adds "User with " prefix)
- [x] **Learn @AfterMapping limitations**: Only works with `@MappingTarget` parameters, not return-type methods
- [x] **Implement custom mapping methods** when @AfterMapping doesn't apply
- [x] **Null value handling strategies** with `NullValueCheckStrategy.ALWAYS` and `NullValuePropertyMappingStrategy.IGNORE`

### ✅ Testing Infrastructure (Comprehensive)
- [x] **Write extensive unit tests** for services with JUnit 5 & Mockito (`PostmanClientServiceTest`, `UserServiceTest`)
- [x] **Use @WebMvcTest** for controller layer testing (`PostmanControllerTest`, `UserControllerTest`)
- [x] **Add integration tests** with `@SpringBootTest` for full application context
- [x] **Use @MockitoBean** instead of deprecated @MockBean for modern Spring Boot testing
- [x] **Comprehensive test coverage** including edge cases, error scenarios, and business logic validation
- [x] **Learn WireMock challenges** - dependency conflicts with Spring Boot 3.5.3 resolved by removal

### 🔄 Firebase Integration (Planned)
- [ ] **Create a user** via the Firebase emulator
- [ ] **User login** implementation

### ✅ Advanced Database Operations
- [x] **Multi-entity MySQL database** with Users and Subjects
- [x] **Create comprehensive Entities, Repositories, and integrate with Controllers**
- [x] **Advanced JPA queries** with custom methods, pagination, and sorting
- [x] **JPA relationships**: One-to-Many (User → Subjects) with proper cascade operations
- [x] **Replace manual mapping code with MapStruct** across all data operations
- [x] **Map between Entity ↔ DTO** with nested object support
- [x] **Use @JsonIgnoreProperties** strategically to prevent circular references and hide sensitive fields

## 🛠️ Tech Stack

- **Framework**: Spring Boot 3.5.3
- **Language**: Java 17
- **Database**: MySQL 8.4.5
- **Mapping**: MapStruct 1.5.5.Final with @AfterMapping patterns
- **HTTP Client**: Spring WebFlux WebClient with @HttpExchange
- **Testing**: JUnit 5 + Mockito + Spring Boot Test
- **Build Tool**: Maven
- **Logging**: SLF4J + Logback
- **Utilities**: Lombok
- **Validation**: Jakarta Bean Validation

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/training/practice/
│   │   ├── client/           # HTTP Client interfaces (@HttpExchange)
│   │   ├── config/           # Configuration classes (HTTP Client, Feature Toggles, Pagination, Security)
│   │   ├── controller/       # REST Controllers (User, UserV2, Subject, Postman, Hello, DI, Config)
│   │   ├── dto/              # Data Transfer Objects (User, Subject, API Response, Backend, External)
│   │   ├── entity/           # JPA Entities (User, Subject with relationships)
│   │   ├── exception/        # Custom exceptions (UserNotFound, EmailExists, etc.)
│   │   ├── mapper/           # MapStruct Mappers (User, Subject, PostmanClient)
│   │   ├── repository/       # JPA Repositories (with custom queries)
│   │   ├── service/          # Business Logic Services (User, Subject, PostmanClient, Greeting)
│   │   └── PracticeApplication.java
│   └── resources/
│       ├── application.yml   # Configuration (Database, HTTP Client, Feature Toggles)
│       └── static/
└── test/                     # Comprehensive test suite (Unit, Integration, Controller)
    ├── java/com/training/practice/
    │   ├── controller/       # @WebMvcTest for controllers
    │   ├── service/          # Unit tests with @MockitoBean
    │   └── integration/      # @SpringBootTest for full context
    └── resources/
        └── application-test.yml
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

### User Management API
```http
# Basic CRUD Operations
GET    /api/users                          # Get all users (with pagination, sorting)
GET    /api/users/{id}                     # Get user by ID
POST   /api/users                          # Create new user
PUT    /api/users/{id}                     # Update user (full)
DELETE /api/users/{id}                     # Delete user

# Advanced Query Operations  
GET    /api/users/department?dept={name}   # Get users by department
GET    /api/users/status/{status}          # Get users by status (ACTIVE/INACTIVE/SUSPENDED)
GET    /api/users/department/{dept}/status/{status}  # Get users by department AND status
GET    /api/users/search?q={query}         # Search users by name

# Subject Management for Users
PATCH  /api/users/{id}/subject             # Add new subject to user
PATCH  /api/users/{id}/status              # Update user status only
```

### User Management API V2
```http
GET    /api/users/v2/{id}                  # Get user by ID (V2 format - simplified response)
```

### Subject Management API
```http
GET    /api/subjects                       # Get all subjects (with pagination, sorting)
GET    /api/subjects/{id}                  # Get subject by ID
GET    /api/subjects/search?q={query}      # Search subjects by name
PATCH  /api/subjects/{id}                  # Update subject (partial)
```

### External API Integration (PostmanClient)
```http
# External User Management via Postman API
GET    /api/external/postman/users         # Fetch all users from external Postman API  
GET    /api/external/postman/users/{id}    # Fetch user by ID from external API
POST   /api/external/postman/users         # Create user via external API
PUT    /api/external/postman/users/{id}    # Update user via external API (full)
PATCH  /api/external/postman/users/{id}    # Update user via external API (partial)
DELETE /api/external/postman/users/{id}    # Delete user via external API

# Additional External Operations
GET    /api/external/postman/users/{id}/permissions  # Get user permissions from external API
GET    /api/external/postman/users/by-role?role={role} # Get users by role from external API
POST   /api/external/postman/users/{id}/roles?role={role} # Assign role to user via external API
```

### System APIs
```http
GET    /api/greeting                       # Simple greeting API with dynamic messages
GET    /api/hello                          # Hello API with greeting service
GET    /api/hello/{name}                   # Hello with path variable
GET    /api/welcome                        # Welcome message API
GET    /api/random                         # Random greeting API
POST   /api/custom                         # Custom greeting with request body

# Dependency Injection Examples
GET    /api/di/casual                      # Casual greeting via DI
GET    /api/di/formal                      # Formal greeting via DI
GET    /api/di/managed                     # Managed greeting with type parameter
GET    /api/di/welcome                     # Managed welcome message
GET    /api/di/staff                       # Random staff greeting

# Configuration Management
GET    /api/config/features                # Get feature flags and configuration
POST   /api/config/features/logging        # Toggle logging feature

GET    /health                             # Application health check
```

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

### 1. **Advanced MapStruct Integration**
- **Comprehensive Entity ↔ DTO mapping** across User, Subject, and PostmanClient domains
- **Null value handling strategies** with `NullValueCheckStrategy.ALWAYS` and `NullValuePropertyMappingStrategy.IGNORE`
- **Nested object mapping** (User → Subjects relationship)
- **Custom mapping configurations** with `componentModel = "spring"`
- **@AfterMapping for post-processing logic**:
  - `PostmanClientMapper.formatUserPostmanStatus()`: Adds "User with " prefix for external API
- **@AfterMapping limitations understood**: Only works with `@MappingTarget` parameters, not return-type methods
- **Custom mapping methods**: Manual implementation when @AfterMapping constraints apply

### 2. **Comprehensive REST Client Integration (@HttpExchange)**
- **Complete `@HttpExchange` interface** (`PostmanClient`) with all HTTP operations:
  - `@GetExchange` for retrieving users and permissions
  - `@PostExchange` for creating users
  - `@PutExchange` for full updates
  - `@PatchExchange` for partial updates and role assignments
  - `@DeleteExchange` for user deletion
- **WebClient configuration** (`PostmanClientConfig`) with:
  - Base URL setup for external APIs
  - Proper timeout configurations
  - Error handling and retry mechanisms
- **External API Controller** (`PostmanController`) providing:
  - Complete CRUD operations for external system integration
  - Bi-directional data transformation
  - Comprehensive error handling for external API calls
- **Bi-directional mapping** (`PostmanClientMapper`):
  - Internal format ↔ External API format transformation
  - Custom @AfterMapping for external API-specific formatting

### 3. **Advanced JPA & Database Features**
- **Multi-entity relationships**: One-to-Many (User → Subjects) with proper cascade operations
- **JPA Auditing** with `@CreationTimestamp` and `@UpdateTimestamp` for automatic timestamp management
- **Advanced repository queries**:
  - Custom finder methods (`findByDepartmentAndStatus`, `findByNameContainingIgnoreCase`)
  - Pagination and sorting support
  - Count queries for analytics (`countByStatus`)
- **Entity validation** with Jakarta Bean Validation annotations
- **Proper foreign key relationships** with cascade and orphan removal

### 4. **JSON Serialization Control**
- **Strategic `@JsonIgnoreProperties`** implementation:
  - Prevent circular references in User ↔ Subject relationships
  - Hide sensitive timestamp fields in API responses
  - Control field visibility per API version (UserV2DTO)
- **Clean API responses** without unwanted internal fields
- **Version-specific DTOs** for different API consumers

### 5. **Comprehensive Configuration Management**
- **Feature toggle configuration** (`FeatureToggleConfig`) for runtime behavior control
- **HTTP client configuration** (`PostmanClientConfig`) for external API integration
- **Pagination configuration** (`PaginationConfig`) with configurable default page sizes
- **Environment-specific settings** with YAML configuration
- **Dependency injection** throughout service layers

### 6. **Advanced Error Handling & Validation**
- **Custom exception hierarchy**:
  - `UserNotFoundException` with descriptive error messages
  - `EmailAlreadyExistsException` for business rule validation
  - `SubjectNotFoundException` for subject operations
- **Global exception handling** with `@ControllerAdvice`
- **Structured error responses** via `ApiResponseDTO`
- **Proper HTTP status codes** for different error scenarios
- **Jakarta Bean Validation** with comprehensive validation rules

### 7. **Comprehensive Logging Infrastructure**
- **Structured logging** with `@Slf4j` annotation throughout the application
- **Request/response tracking** with request IDs for traceability
- **Performance logging** for service operations
- **Error logging** with detailed context information
- **Debug logging** for troubleshooting and development

### 8. **Production-Ready Testing Infrastructure**
- **Comprehensive unit tests** with JUnit 5 and Mockito:
  - Service layer testing (`PostmanClientServiceTest`, `UserServiceTest`)
  - Business logic validation and edge case coverage
- **Controller layer testing** with `@WebMvcTest`:
  - HTTP endpoint testing (`PostmanControllerTest`, `UserControllerTest`) 
  - Request/response validation and error scenario testing
- **Integration testing** with `@SpringBootTest`:
  - Full application context testing
  - Database integration validation
- **Modern testing practices**:
  - `@MockitoBean` instead of deprecated `@MockBean`
  - Comprehensive test coverage across all layers
  - Mock external dependencies and API calls

## 🧪 Testing Examples

### Manual API Testing with Postman/cURL

#### User Management Operations
```bash
# Create a new user
curl --location 'http://localhost:8888/api/users' \
--header 'Content-Type: application/json' \
--header 'X-Request-ID: create-user-001' \
--header 'X-Client-Version: 2.0' \
--data '{
    "name": "John Doe",
    "email": "john.doe@example.com",
    "phone": "+1234567890",
    "department": "Engineering",
    "status": "ACTIVE"
}'

# Get user with subjects
curl --location 'http://localhost:8888/api/users/3e0015bc-2060-46c7-a76f-00e55e0b56fb' \
--header 'X-Request-ID: get-user-1753621943'

# Get users by department and status
curl --location 'http://localhost:8888/api/users/department/Engineering/status/ACTIVE' \
--header 'X-Request-ID: filter-users-001'

# Search users by name
curl --location 'http://localhost:8888/api/users/search?q=John' \
--header 'X-Request-ID: search-users-001'

# Add subject to user
curl --location --request PATCH 'http://localhost:8888/api/users/3e0015bc-2060-46c7-a76f-00e55e0b56fb/subject' \
--header 'Content-Type: application/json' \
--header 'X-Request-ID: add-subject-002' \
--data '{
    "name": "Computer Science",
    "description": "Introduction to programming and algorithms",
    "code": "CS1"
}'

# Get paginated users
curl --location 'http://localhost:8888/api/users?page=0&size=10&sort=name&direction=ASC' \
--header 'X-Request-ID: paginate-users-001'
```

#### External API Integration Testing
```bash
# Fetch users from external Postman API
curl --location 'http://localhost:8888/api/external/postman/users' \
--header 'X-Request-ID: external-get-all-001'

# Create user via external API
curl --location 'http://localhost:8888/api/external/postman/users' \
--header 'Content-Type: application/json' \
--header 'X-Request-ID: external-create-001' \
--data '{
    "name": "External User",
    "email": "external@example.com",
    "department": "External Dept"
}'

# Get user permissions from external API
curl --location 'http://localhost:8888/api/external/postman/users/123/permissions' \
--header 'X-Request-ID: external-permissions-001'
```

#### Subject Management Testing
```bash
# Get subjects with pagination
curl --location 'http://localhost:8888/api/subjects?page=0&size=5&sort=name&direction=DESC' \
--header 'X-Request-ID: subjects-paginate-001'

# Search subjects
curl --location 'http://localhost:8888/api/subjects/search?q=Computer&limit=10' \
--header 'X-Request-ID: subjects-search-001'

# Update subject
curl --location --request PATCH 'http://localhost:8888/api/subjects/subject-id-123' \
--header 'Content-Type: application/json' \
--header 'X-Request-ID: subjects-update-001' \
--data '{
    "name": "Advanced Computer Science",
    "description": "Advanced programming concepts"
}'
```

### Automated Testing Infrastructure

#### Unit Tests (`src/test/java/com/training/practice/service/`)
```java
@ExtendWith(MockitoExtension.class)
class PostmanClientServiceTest {
    @Mock
    private PostmanClient postmanClient;
    
    @Mock
    private PostmanClientMapper mapper;
    
    @InjectMocks
    private PostmanClientService service;
    
    @Test
    void shouldGetAllUsersSuccessfully() {
        // Test implementation with comprehensive coverage
    }
}
```

#### Controller Tests (`src/test/java/com/training/practice/controller/`)
```java
@WebMvcTest(PostmanController.class)
class PostmanControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    @MockitoBean
    private PostmanClientService service;
    
    @Test
    void shouldReturnAllUsersWithValidResponse() throws Exception {
        // HTTP endpoint testing with MockMvc
    }
}
```

#### Integration Tests (`src/test/java/com/training/practice/`)
```java
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
class UserServiceIntegrationTest {
    @Autowired
    private UserService userService;
    
    @Test
    void shouldCreateAndRetrieveUserSuccessfully() {
        // Full application context testing
    }
}
```

## 🔄 Development Highlights & Lessons Learned

### ✅ **MapStruct Deep Dive**
- **Successfully implemented comprehensive mapping patterns** across User, Subject, and PostmanClient domains
- **@AfterMapping mastery**: Learned critical limitation that @AfterMapping only works with `@MappingTarget` parameters, not return-type methods
- **Practical @AfterMapping implementations**:
  - `PostmanClientMapper.formatUserPostmanStatus()`: Adds "User with " prefix for external API compatibility
- **Null value strategies**: Configured `NullValueCheckStrategy.ALWAYS` and `NullValuePropertyMappingStrategy.IGNORE` for robust mapping
- **Workaround for @AfterMapping limitations**: Implemented custom mapping methods when @AfterMapping constraints apply

### ✅ **External API Integration Excellence**
- **Complete @HttpExchange implementation**: Full CRUD operations with PostmanClient interface
- **Production-ready WebClient configuration**: Proper timeouts, error handling, and base URL management
- **Bi-directional mapping success**: Internal ↔ External API format transformation with PostmanClientMapper
- **Comprehensive external controller**: PostmanController provides complete integration layer for external systems

### ✅ **Advanced Database & JPA Patterns**
- **Multi-entity relationship mastery**: Successfully implemented One-to-Many (User → Subjects) with proper cascade operations
- **JPA auditing implementation**: Automatic timestamp management with @CreationTimestamp and @UpdateTimestamp
- **Advanced query capabilities**: Custom repository methods, pagination, sorting, and complex filtering
- **Entity validation**: Comprehensive Jakarta Bean Validation integration

### ✅ **Testing Infrastructure Excellence**
- **Comprehensive test coverage**: Unit tests, controller tests (@WebMvcTest), and integration tests (@SpringBootTest)
- **Modern testing practices**: Successfully migrated to @MockitoBean from deprecated @MockBean
- **WireMock challenges resolved**: Learned dependency conflicts with Spring Boot 3.5.3 and implemented alternative approaches
- **Production-ready test patterns**: Complete test infrastructure for enterprise applications

### ✅ **Configuration Management Mastery**
- **Feature toggle implementation**: Runtime behavior control via FeatureToggleConfig
- **HTTP client configuration**: External API integration settings via PostmanClientConfig  
- **Pagination configuration**: Configurable default page sizes via PaginationConfig
- **Environment-specific settings**: Proper YAML configuration management

## 🎯 Next Development Phases

### Phase 1: Enhanced Testing & Validation
- **OpenAPI/Swagger documentation**: Auto-generate comprehensive API documentation
- **Advanced validation scenarios**: Complex business rule validation with custom validators
- **Performance testing**: Load testing for high-traffic scenarios
- **Contract testing**: API contract validation between internal and external systems

### Phase 2: Security & Authentication
- **Firebase Authentication integration**: User login and authentication flows
- **JWT token management**: Secure API access with token-based authentication
- **Role-based access control**: Implement user roles and permissions
- **API rate limiting**: Protect APIs from abuse and ensure fair usage

### Phase 3: Advanced Features
- **Caching implementation**: Redis integration for performance optimization
- **Event-driven architecture**: Implement domain events and event handlers
- **Async processing**: Background job processing with @Async and queues
- **Monitoring & observability**: Metrics, tracing, and health monitoring

### Phase 4: Production Readiness
- **Docker containerization**: Container-ready deployment setup
- **CI/CD pipeline**: Automated testing and deployment workflows
- **Environment management**: Production, staging, and development environment configuration
- **Logging aggregation**: Centralized logging with structured log analysis

## 📚 Key Learning Outcomes & Technical Mastery

### Core Spring Boot Ecosystem
- ✅ **Spring Boot 3.5.3 mastery**: Advanced configuration, dependency injection, and auto-configuration
- ✅ **Spring Data JPA expertise**: Complex relationships, custom queries, pagination, and auditing
- ✅ **Spring WebFlux integration**: @HttpExchange interfaces and WebClient configuration
- ✅ **Spring Security foundations**: Security configuration and validation patterns

### Advanced Object Mapping (MapStruct)
- ✅ **@AfterMapping patterns and limitations**: Deep understanding of when and how to use @AfterMapping
- ✅ **Custom mapping strategies**: Implementing manual mapping when framework limitations apply
- ✅ **Null value handling**: Production-ready null value strategies for robust applications
- ✅ **Nested object mapping**: Complex entity relationships with bidirectional mapping

### External System Integration
- ✅ **@HttpExchange mastery**: Complete REST client implementation with all HTTP methods
- ✅ **WebClient configuration**: Production-ready HTTP client setup with timeouts and error handling
- ✅ **Bi-directional data transformation**: Internal ↔ External API format mapping
- ✅ **Error handling for external APIs**: Graceful degradation and retry mechanisms

### Testing Excellence
- ✅ **Multi-layer testing strategy**: Unit, integration, and controller testing patterns
- ✅ **Modern testing practices**: @MockitoBean usage and Spring Boot 3.x testing patterns
- ✅ **Comprehensive test coverage**: Business logic, HTTP endpoints, and database operations
- ✅ **External dependency mocking**: Testing external API integrations effectively

### Database & Persistence Patterns
- ✅ **Advanced JPA relationships**: One-to-Many with cascade operations and orphan removal
- ✅ **Repository pattern mastery**: Custom queries, pagination, and complex filtering
- ✅ **Entity validation**: Jakarta Bean Validation with comprehensive validation rules
- ✅ **JSON serialization control**: Strategic @JsonIgnoreProperties for clean APIs

### Enterprise Development Practices
- ✅ **Configuration management**: Feature toggles, environment-specific settings, and externalized configuration
- ✅ **Error handling patterns**: Global exception handling with structured error responses
- ✅ **Logging best practices**: Structured logging, request tracking, and performance monitoring
- ✅ **API design excellence**: RESTful design, versioning, pagination, and search capabilities

---

**🎓 This project demonstrates comprehensive mastery of the Spring Boot ecosystem, from core fundamentals to advanced enterprise patterns. The codebase serves as a reference implementation for production-ready Spring Boot applications with external system integration, advanced mapping patterns, and comprehensive testing strategies.**
