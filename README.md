# Social Media Backend

A scalable, microservices-based social media platform built with Spring Boot, featuring real-time notifications, media management, analytics, and comprehensive API documentation.

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.2-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

## 📋 Table of Contents

- [Features](#features)
- [Architecture](#architecture)
- [Technologies](#technologies)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [API Documentation](#api-documentation)
- [Project Structure](#project-structure)
- [Configuration](#configuration)
- [Testing](#testing)
- [Contributing](#contributing)
- [License](#license)

## ✨ Features

### Core Functionality
- 🔐 **User Authentication & Authorization** - JWT-based secure authentication
- 👤 **User Profiles** - Create, update, and manage user profiles
- 📝 **Posts & Comments** - Create posts, comment, and engage with content
- ❤️ **Likes & Reactions** - Like posts and view engagement metrics
- 👥 **Follow System** - Follow/unfollow users and build social connections
- 📊 **Analytics Dashboard** - Real-time analytics and metrics
- 📁 **Media Management** - Upload and manage images/videos with MinIO
- 🔔 **Notifications** - Event-driven notification system

### Technical Features
- 🚀 **Microservices Architecture** - Independent, scalable services
- 📄 **Pagination & Sorting** - Efficient data retrieval for all list endpoints
- 💾 **Redis Caching** - High-performance caching layer
- 📨 **Event-Driven** - Kafka-based asynchronous messaging
- 🔍 **API Documentation** - Interactive Swagger UI for all services
- ✅ **Input Validation** - Comprehensive request validation
- 🛡️ **Global Exception Handling** - Consistent error responses
- ⚡ **Rate Limiting** - Request throttling for API protection
- 🔄 **Version Control Ready** - Git-friendly structure

## 🏗️ Architecture

The system follows a **microservices architecture** with the following components:

```
┌─────────────────────────────────────────────────────────────────┐
│                         API Gateway (Future)                     │
└─────────────────────────────────────────────────────────────────┘
                                  │
        ┌─────────────────────────┼─────────────────────────┐
        │                         │                         │
┌───────▼────────┐      ┌────────▼────────┐      ┌────────▼────────┐
│  Auth Service  │      │  User Service   │      │  Post Service   │
│   Port: 8080   │      │   Port: 8081    │      │   Port: 8082    │
└────────────────┘      └─────────────────┘      └─────────────────┘
        │                         │                         │
        │                         │                         │
┌───────▼────────┐      ┌────────▼────────┐      ┌────────▼────────┐
│ Media Service  │      │ Notification    │      │ Analytics       │
│  Port: 4070    │      │   Port: 4060    │      │  Port: 4090     │
└────────────────┘      └─────────────────┘      └─────────────────┘
        │                         │                         │
        └─────────────────────────┼─────────────────────────┘
                                  │
        ┌─────────────────────────┼─────────────────────────┐
        │                         │                         │
┌───────▼────────┐      ┌────────▼────────┐      ┌────────▼────────┐
│    MongoDB     │      │     Kafka       │      │     Redis       │
│  Port: 27017   │      │   Port: 9092    │      │   Port: 6379    │
└────────────────┘      └─────────────────┘      └─────────────────┘
                                  │
                        ┌─────────▼─────────┐
                        │      MinIO        │
                        │  Port: 9000/9001  │
                        └───────────────────┘
```

### Service Responsibilities

| Service | Description | Port |
|---------|-------------|------|
| **Auth Service** | User authentication, JWT token generation | 8080 |
| **User Service** | User profiles, follow/unfollow functionality | 8081 |
| **Post Service** | Posts, comments, likes, feed management | 8082 |
| **Media Service** | Image/video upload and storage with MinIO | 4070 |
| **Notification Service** | Email notifications for events | 4060 |
| **Analytics Service** | Global metrics and statistics | 4090 |

## 🛠️ Technologies

### Backend Framework
- **Spring Boot 4.0.2** - Main framework
- **Spring Security 7.0.2** - Authentication & authorization
- **Spring Data MongoDB** - Database integration
- **Spring Kafka 4.1.1** - Event streaming

### Databases & Storage
- **MongoDB** - Primary database
- **Redis 7** - Caching layer
- **MinIO** - Object storage for media files

### Messaging & Communication
- **Apache Kafka 7.6.0** - Event-driven messaging
- **JSON** - Data serialization

### API & Documentation
- **SpringDoc OpenAPI 2.5.0** - API documentation
- **Swagger UI** - Interactive API testing

### Security
- **JWT (JJWT 0.12.6)** - Token-based authentication
- **BCrypt** - Password hashing

### Development Tools
- **Docker & Docker Compose** - Containerization
- **Maven** - Build automation
- **Lombok** - Code generation

### Additional Libraries
- **Bucket4j 8.0.1** - Rate limiting
- **Jackson** - JSON processing
- **Jakarta Bean Validation** - Input validation

## 📦 Prerequisites

Before running this project, ensure you have:

- **Java 21** or higher
- **Docker** and **Docker Compose**
- **Maven 3.8+** (optional, Maven wrapper included)
- **Git**
- **Postman** (optional, for API testing)

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/jaisheel15/Social_Media_Backend.git
cd Social_Media_Backend
```

### 2. Start Infrastructure Services

Start all required infrastructure (MongoDB, Kafka, Redis, MinIO):

```bash
docker-compose up -d
```

This will start:
- MongoDB (localhost:27017)
- Kafka (localhost:9092)
- Redis (localhost:6379)
- MinIO (localhost:9000, Console: 9001)
- All microservices (auth, user, post, media, notification, analytics)

### 3. Verify Services

Check if all services are running:

```bash
docker ps
```

You should see all containers in a healthy state.

### 4. Access Swagger UI

Each service provides interactive API documentation:

- **Auth Service**: http://localhost:8080/swagger-ui.html
- **User Service**: http://localhost:8081/swagger-ui.html
- **Post Service**: http://localhost:8082/swagger-ui.html
- **Media Service**: http://localhost:4070/swagger-ui.html
- **Analytics Service**: http://localhost:4090/swagger-ui.html
- **Notification Service**: http://localhost:4060/swagger-ui.html

### 5. MinIO Console

Access MinIO console for media management:

- **URL**: http://localhost:9001
- **Username**: `minioadmin`
- **Password**: `minioadmin`

## 📚 API Documentation

### Quick Start Guide

Detailed API testing instructions are available in:
- **[POSTMAN_TESTING_GUIDE.md](POSTMAN_TESTING_GUIDE.md)** - Complete Postman testing guide
- **[PAGINATION_GUIDE.md](PAGINATION_GUIDE.md)** - Pagination and sorting documentation

### Authentication Flow

1. **Register a new user**
   ```http
   POST http://localhost:8080/auth/register
   Content-Type: application/json

   {
     "username": "john_doe",
     "email": "john@example.com",
     "password": "password123"
   }
   ```

2. **Login to get JWT token**
   ```http
   POST http://localhost:8080/auth/login
   Content-Type: application/json

   {
     "username": "john_doe",
     "password": "password123"
   }
   ```

3. **Use the JWT token for authenticated requests**
   ```http
   Authorization: Bearer <your_jwt_token>
   ```

### Key API Endpoints

#### User Service (Port 8081)
- `POST /users/` - Create user profile
- `GET /users/me` - Get current user
- `PUT /users/` - Update user profile
- `POST /users/follow` - Follow a user
- `GET /users/followers?page=0&size=20` - Get followers (paginated)
- `GET /users/following?page=0&size=20` - Get following (paginated)

#### Post Service (Port 8082)
- `POST /posts/` - Create a post
- `GET /posts/feed?page=0&size=10` - Get feed (paginated)
- `GET /posts/author/{authorId}?page=0&size=10` - Get user's posts
- `POST /posts/{id}/like` - Like a post
- `POST /posts/{id}/comment` - Comment on a post
- `DELETE /posts/{id}` - Delete a post

#### Media Service (Port 4070)
- `POST /media/upload` - Upload media file
- `GET /media/user/?page=0&size=20` - Get user media (paginated)
- `GET /media/{id}` - Get media by ID

#### Analytics Service (Port 4090)
- `GET /analytics/overview` - Get global metrics

## 📁 Project Structure

```
Social_Media_Backend/
├── auth_service/               # Authentication service
│   ├── src/main/java/com/example/auth_service/
│   │   ├── controller/        # REST controllers
│   │   ├── service/           # Business logic
│   │   ├── repository/        # Database access
│   │   ├── model/             # Data models
│   │   ├── dto/               # Data transfer objects
│   │   ├── config/            # Configuration classes
│   │   └── exception/         # Exception handling
│   └── pom.xml
│
├── user_service/               # User management service
├── post_service/               # Posts and comments service
├── media_service/              # Media upload service
├── notification_service/       # Notification service
├── analytics_service/          # Analytics service
│
├── docker-compose.yml          # Docker orchestration
├── README.md                   # This file
├── POSTMAN_TESTING_GUIDE.md    # API testing guide
└── PAGINATION_GUIDE.md         # Pagination documentation
```

### Service Structure

Each microservice follows this structure:

```
service_name/
├── src/
│   ├── main/
│   │   ├── java/com/example/service_name/
│   │   │   ├── controller/          # REST endpoints
│   │   │   ├── service/             # Business logic
│   │   │   ├── repository/          # Database operations
│   │   │   ├── model/               # Domain models
│   │   │   ├── dto/                 # Request/Response DTOs
│   │   │   ├── config/              # Configuration
│   │   │   │   ├── SecurityConfig   # Security setup
│   │   │   │   ├── CacheConfig      # Redis caching
│   │   │   │   └── OpenApiConfig    # Swagger config
│   │   │   ├── event/               # Kafka producers/consumers
│   │   │   └── exception/           # Error handling
│   │   └── resources/
│   │       └── application.properties
│   └── test/                        # Unit tests
├── Dockerfile
└── pom.xml
```

## ⚙️ Configuration

### Environment Variables

Key environment variables used in docker-compose.yml:

```yaml
# MongoDB
SPRING_DATA_MONGODB_URI: mongodb://mongodb:27017/[dbname]

# Redis
SPRING_DATA_REDIS_HOST: redis
SPRING_DATA_REDIS_PORT: 6379

# Kafka
SPRING_KAFKA_BOOTSTRAP_SERVERS: kafka:9092

# MinIO (Media Service only)
MINIO_URL: http://minio:9000
MINIO_ACCESS_KEY: minioadmin
MINIO_SECRET_KEY: minioadmin
MINIO_BUCKET: media-bucket
```

### Application Properties

Each service has its own `application.properties`:

```properties
# Server Configuration
server.port=8080

# MongoDB Configuration
spring.data.mongodb.uri=${SPRING_DATA_MONGODB_URI}

# Redis Configuration
spring.data.redis.host=${SPRING_DATA_REDIS_HOST:localhost}
spring.data.redis.port=${SPRING_DATA_REDIS_PORT:6379}

# Kafka Configuration (for applicable services)
spring.kafka.bootstrap-servers=${SPRING_KAFKA_BOOTSTRAP_SERVERS:localhost:9092}

# Swagger Configuration
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.swagger-ui.enabled=true
```

### Cache TTL Configuration

Redis cache timeouts by service:
- **Auth Service**: 15 minutes
- **User Service**: 10 minutes
- **Post Service**: 10 minutes
- **Media Service**: 30 minutes
- **Analytics Service**: 5 minutes
- **Notification Service**: 10 minutes

## 🧪 Testing

### Using Postman

1. Import the environment and requests from [POSTMAN_TESTING_GUIDE.md](POSTMAN_TESTING_GUIDE.md)
2. Follow the testing order:
   - Register → Login → Create Profile → Create Posts → Engage

### Using Swagger UI

1. Navigate to service's Swagger UI (e.g., http://localhost:8081/swagger-ui.html)
2. Click **Authorize** button
3. Enter: `Bearer <your_jwt_token>`
4. Test endpoints directly in the browser

### Running Unit Tests

```bash
# Test a specific service
cd auth_service
./mvnw test

# Test all services
docker-compose exec auth-service ./mvnw test
docker-compose exec user-service ./mvnw test
docker-compose exec post-service ./mvnw test
```

## 🔧 Development

### Building Services Locally

```bash
# Build a specific service
cd auth_service
./mvnw clean install

# Build all services
./build-all.sh  # Create this script if needed
```

### Rebuilding Docker Containers

```bash
# Rebuild all services
docker-compose build

# Rebuild specific service
docker-compose build user-service

# Rebuild and restart
docker-compose up -d --build
```

### Viewing Logs

```bash
# View all logs
docker-compose logs -f

# View specific service logs
docker-compose logs -f user-service

# View last 100 lines
docker-compose logs --tail=100 -f post-service
```

### Stopping Services

```bash
# Stop all services
docker-compose down

# Stop and remove volumes (clears data)
docker-compose down -v

# Stop specific service
docker-compose stop user-service
```

## 📊 Features Deep Dive

### 1. Pagination & Sorting

All list endpoints support pagination and sorting:

```http
GET /users/followers?page=0&size=20&sort=createdAt,desc
GET /posts/feed?page=0&size=10&sort=likeCount,desc
GET /media/user/?page=0&size=30&sort=uploadedAt,desc
```

See [PAGINATION_GUIDE.md](PAGINATION_GUIDE.md) for details.

### 2. Caching Strategy

Redis caching is implemented for:
- User profiles
- Posts
- User email lookups
- Followers/following counts

Non-paginated data is cached; paginated endpoints hit the database directly for consistency.

### 3. Event-Driven Architecture

Kafka topics:
- `USER_CREATED_EVENTS` - User registration
- `POST_CREATED_EVENTS` - New posts
- `POST_LIKED_EVENTS` - Post likes
- `POST_COMMENT_EVENTS` - Comments
- `USER_FOLLOW_EVENTS` - Follow actions

### 4. Security Features

- JWT-based authentication
- BCrypt password hashing
- Rate limiting per endpoint
- Input validation on all requests
- CORS configuration
- Secure file upload validation

### 5. Error Handling

Global exception handlers provide consistent error responses:

```json
{
  "timestamp": "2026-01-31T10:30:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/users/"
}
```

## 🐛 Troubleshooting

### Common Issues

1. **Services won't start**
   ```bash
   # Check port conflicts
   lsof -i :8080
   
   # Restart Docker
   docker-compose down
   docker-compose up -d
   ```

2. **MongoDB connection issues**
   ```bash
   # Check MongoDB is running
   docker-compose logs mongodb
   
   # Restart MongoDB
   docker-compose restart mongodb
   ```

3. **Kafka consumer lag**
   ```bash
   # Check Kafka logs
   docker-compose logs kafka
   
   # Reset Kafka topics (development only)
   docker-compose down -v
   docker-compose up -d
   ```

4. **Redis cache issues**
   ```bash
   # Clear Redis cache
   docker exec -it redis redis-cli FLUSHALL
   ```

5. **Build failures**
   ```bash
   # Clean Maven cache
   ./mvnw clean
   
   # Rebuild without cache
   docker-compose build --no-cache
   ```

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Coding Standards

- Follow Java naming conventions
- Use Lombok annotations appropriately
- Add JavaDoc for public methods
- Write unit tests for new features
- Update API documentation
- Follow REST API best practices

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Authors

- **Jaisheel** - [@jaisheel15](https://github.com/jaisheel15)

## 📞 Support

For questions or issues:
- Open an [Issue](https://github.com/jaisheel15/Social_Media_Backend/issues)
- Check [POSTMAN_TESTING_GUIDE.md](POSTMAN_TESTING_GUIDE.md)
- Review [PAGINATION_GUIDE.md](PAGINATION_GUIDE.md)



**Made with ❤️ using Spring Boot**
