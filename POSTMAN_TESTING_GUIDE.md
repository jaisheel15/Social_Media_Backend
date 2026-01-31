# Postman Testing Guide - Social Media Backend

This comprehensive guide will help you test all APIs in the Social Media Backend using Postman.

## Table of Contents
1. [Prerequisites](#prerequisites)
2. [Environment Setup](#environment-setup)
3. [Service Ports](#service-ports)
4. [Testing Order](#testing-order)
5. [Auth Service APIs](#auth-service-apis)
6. [User Service APIs](#user-service-apis)
7. [Post Service APIs](#post-service-apis)
8. [Media Service APIs](#media-service-apis)
9. [Analytics Service APIs](#analytics-service-apis)
10. [Common Issues & Solutions](#common-issues--solutions)

---

## Prerequisites

1. **Start Docker Services**:
   ```bash
   cd /home/jaisheel/Desktop/Project/Social_Media_Backend
   docker-compose up -d
   ```

2. **Verify Services are Running**:
   ```bash
   docker ps
   ```
   You should see: kafka, mongodb, redis, minio, auth-service, user-service, post-service, media-service, notification-service, analytics-service

3. **Install Postman**: Download from [postman.com](https://www.postman.com/downloads/)

---

## Environment Setup

### Create Postman Environment

1. Click on **Environments** → **Create Environment**
2. Name it: `Social Media Backend - Local`
3. Add these variables:

| Variable | Initial Value | Current Value |
|----------|---------------|---------------|
| `base_url` | `http://localhost` | `http://localhost` |
| `auth_port` | `8080` | `8080` |
| `user_port` | `8081` | `8081` |
| `post_port` | `8082` | `8082` |
| `media_port` | `4070` | `4070` |
| `analytics_port` | `4090` | `4090` |
| `jwt_token` | | (auto-populated after login) |
| `user_id` | | (auto-populated after user creation) |
| `post_id` | | (auto-populated after post creation) |

4. **Save** the environment and **select it** from the dropdown

---

## Service Ports

| Service | Port | Base URL |
|---------|------|----------|
| Auth Service | 8080 | `http://localhost:8080` |
| User Service | 8081 | `http://localhost:8081` |
| Post Service | 8082 | `http://localhost:8082` |
| Media Service | 4070 | `http://localhost:4070` |
| Analytics Service | 4090 | `http://localhost:4090` |
| Notification Service | 4060 | `http://localhost:4060` |

---

## Testing Order

Follow this sequence for end-to-end testing:

1. ✅ **Register User** (Auth Service)
2. ✅ **Login** (Auth Service) → Save JWT token
3. ✅ **Create User Profile** (User Service)
4. ✅ **Get Current User** (User Service)
5. ✅ **Create Post** (Post Service)
6. ✅ **Like Post** (Post Service)
7. ✅ **Comment on Post** (Post Service)
8. ✅ **Get Feed** (Post Service)
9. ✅ **Follow User** (User Service)
10. ✅ **Get Analytics** (Analytics Service)

---

## Auth Service APIs

### Base URL: `{{base_url}}:{{auth_port}}`

### 1. Register User

**Endpoint**: `POST /auth/register`

**Headers**:
```
Content-Type: application/json
```

**Body** (raw JSON):
```json
{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "password123"
}
```

**Expected Response**: `200 OK`
```json
{}
```

**Test Script** (Tests tab):
```javascript
pm.test("Status code is 200", function () {
    pm.response.to.have.status(200);
});
```

---

### 2. Login

**Endpoint**: `POST /auth/login`

**Headers**:
```
Content-Type: application/json
```

**Body** (raw JSON):
```json
{
  "username": "john_doe",
  "password": "password123"
}
```

**Expected Response**: `200 OK`
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

**Test Script** (Tests tab):
```javascript
pm.test("Status code is 200", function () {
    pm.response.to.have.status(200);
});

pm.test("Token is present", function () {
    var jsonData = pm.response.json();
    pm.expect(jsonData.token).to.be.a('string');
    pm.environment.set("jwt_token", jsonData.token);
});
```

**⚠️ Important**: The JWT token is automatically saved to environment variable `jwt_token`

---

## User Service APIs

### Base URL: `{{base_url}}:{{user_port}}`

### Authentication Required
All User Service endpoints (except internal ones) require JWT token:

**Headers**:
```
Authorization: Bearer {{jwt_token}}
Content-Type: application/json
```

---

### 1. Create User Profile

**Endpoint**: `POST /users/`

**Headers**:
```
Authorization: Bearer {{jwt_token}}
Content-Type: application/json
```

**Body** (raw JSON):
```json
{
  "username": "john_doe",
  "email": "john@example.com",
  "bio": "Software developer and tech enthusiast",
  "avatarUrl": "https://example.com/avatar.jpg"
}
```

**Expected Response**: `200 OK`
```json
{
  "id": "65f1a2b3c4d5e6f7g8h9i0j1",
  "username": "john_doe",
  "email": "john@example.com",
  "authUserId": "auth_user_id",
  "avatarUrl": "https://example.com/avatar.jpg",
  "bio": "Software developer and tech enthusiast",
  "followersCount": 0,
  "followingCount": 0,
  "createdAt": "2026-01-31T10:30:00Z"
}
```

**Test Script**:
```javascript
pm.test("Status code is 200", function () {
    pm.response.to.have.status(200);
});

pm.test("User ID is saved", function () {
    var jsonData = pm.response.json();
    pm.environment.set("user_id", jsonData.id);
});
```

---

### 2. Get Current User

**Endpoint**: `GET /users/me`

**Headers**:
```
Authorization: Bearer {{jwt_token}}
```

**Expected Response**: `200 OK`
```json
{
  "id": "65f1a2b3c4d5e6f7g8h9i0j1",
  "username": "john_doe",
  "email": "john@example.com",
  "authUserId": "auth_user_id",
  "avatarUrl": "https://example.com/avatar.jpg",
  "bio": "Software developer and tech enthusiast",
  "followersCount": 0,
  "followingCount": 0,
  "createdAt": "2026-01-31T10:30:00Z"
}
```

---

### 3. Get User by ID

**Endpoint**: `GET /users/{{user_id}}`

**Headers**:
```
Authorization: Bearer {{jwt_token}}
```

**Expected Response**: `200 OK` - Same as Get Current User

---

### 4. Update User Profile

**Endpoint**: `PUT /users/`

**Headers**:
```
Authorization: Bearer {{jwt_token}}
Content-Type: application/json
```

**Body** (raw JSON):
```json
{
  "username": "john_doe_updated",
  "bio": "Updated bio - Full stack developer",
  "avatarUrl": "https://example.com/new-avatar.jpg",
  "email": "john.updated@example.com"
}
```

**Note**: All fields are optional. Only send fields you want to update.

**Expected Response**: `200 OK`
```json
{
  "id": "65f1a2b3c4d5e6f7g8h9i0j1",
  "username": "john_doe_updated",
  "email": "john.updated@example.com",
  "bio": "Updated bio - Full stack developer",
  "avatarUrl": "https://example.com/new-avatar.jpg",
  ...
}
```

---

### 5. Follow User

**Endpoint**: `POST /users/follow`

**Headers**:
```
Authorization: Bearer {{jwt_token}}
Content-Type: application/json
```

**Body** (raw JSON):
```json
{
  "targetUserId": "65f1a2b3c4d5e6f7g8h9i0j2"
}
```

**Expected Response**: `200 OK`
```json
"Successfully followed the user"
```

---

### 6. Get Followers (with Pagination)

**Endpoint**: `GET /users/followers?page=0&size=20&sort=createdAt,desc`

⚠️ **Important**: Do NOT add a trailing slash. Use `/users/followers` not `/users/followers/`

**Headers**:
```
Authorization: Bearer {{jwt_token}}
```

**Query Parameters**:
- `page` (optional): Page number (0-indexed) - Default: 0
- `size` (optional): Items per page - Default: 20
- `sort` (optional): Sort field and direction - Default: createdAt

**Expected Response**: `200 OK`
```json
{
  "content": [
    {
      "id": "65f1a2b3c4d5e6f7g8h9i0j2",
      "username": "jane_doe",
      "email": "jane@example.com",
      "avatarUrl": "https://example.com/jane-avatar.jpg",
      "bio": "Designer",
      "followersCount": 10,
      "followingCount": 15,
      "createdAt": "2026-01-30T10:30:00Z"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 20
  },
  "totalElements": 1,
  "totalPages": 1,
  "last": true,
  "first": true,
  "numberOfElements": 1,
  "empty": false
}
```

---

### 7. Get Following (with Pagination)

**Endpoint**: `GET /users/following?page=0&size=20&sort=createdAt,desc`

⚠️ **Important**: Do NOT add a trailing slash. Use `/users/following` not `/users/following/`

**Headers**:
```
Authorization: Bearer {{jwt_token}}
```

**Query Parameters**: Same as Get Followers

**Expected Response**: Same format as Get Followers

---

## Post Service APIs

### Base URL: `{{base_url}}:{{post_port}}`

### Authentication Required
All Post Service endpoints require JWT token.

---

### 1. Create Post

**Endpoint**: `POST /posts/`

**Headers**:
```
Authorization: Bearer {{jwt_token}}
Content-Type: application/json
```

**Body** (raw JSON):
```json
{
  "title": "My First Post",
  "content": "This is the content of my first post. It's about sharing experiences!",
  "authorId": "{{user_id}}",
  "media": []
}
```

**Expected Response**: `200 OK`
```json
{
  "id": "65f1a2b3c4d5e6f7g8h9i0j3",
  "title": "My First Post",
  "content": "This is the content of my first post. It's about sharing experiences!",
  "authorId": "65f1a2b3c4d5e6f7g8h9i0j1",
  "commentsIds": [],
  "likesIds": [],
  "likeCount": 0,
  "media": [],
  "createdAt": "2026-01-31T10:35:00Z"
}
```

**Test Script**:
```javascript
pm.test("Status code is 200", function () {
    pm.response.to.have.status(200);
});

pm.test("Post ID is saved", function () {
    var jsonData = pm.response.json();
    pm.environment.set("post_id", jsonData.id);
});
```

---

### 2. Get Post by ID

**Endpoint**: `GET /posts/{{post_id}}`

**Headers**:
```
Authorization: Bearer {{jwt_token}}
```

**Expected Response**: `200 OK` - Same as Create Post response

---

### 3. Like Post

**Endpoint**: `POST /posts/{{post_id}}/like`

**Headers**:
```
Authorization: Bearer {{jwt_token}}
Content-Type: application/json
```

**Body** (raw JSON):
```json
{
  "userId": "{{user_id}}"
}
```

**Expected Response**: `200 OK`
```json
{
  "id": "65f1a2b3c4d5e6f7g8h9i0j4",
  "postId": "65f1a2b3c4d5e6f7g8h9i0j3",
  "userId": "65f1a2b3c4d5e6f7g8h9i0j1"
}
```

---

### 4. Comment on Post

**Endpoint**: `POST /posts/{{post_id}}/comment`

**Headers**:
```
Authorization: Bearer {{jwt_token}}
Content-Type: application/json
```

**Body** (raw JSON):
```json
{
  "postId": "{{post_id}}",
  "userId": "{{user_id}}",
  "content": "Great post! Very informative."
}
```

**Expected Response**: `200 OK`
```json
"Comment Posted"
```

---

### 5. Delete Post

**Endpoint**: `DELETE /posts/{{post_id}}`

**Headers**:
```
Authorization: Bearer {{jwt_token}}
```

**Expected Response**: `200 OK`
```json
"Post Deleted"
```

---

### 6. Get Feed (with Pagination)

**Endpoint**: `GET /posts/feed?page=0&size=10&sort=createdAt,desc`

**Headers**:
```
Authorization: Bearer {{jwt_token}}
```

**Query Parameters**:
- `page` (optional): Page number - Default: 0
- `size` (optional): Items per page - Default: 10
- `sort` (optional): Sort options - Default: createdAt
  - `createdAt,desc` - Latest first
  - `createdAt,asc` - Oldest first
  - `likeCount,desc` - Most liked first

**Expected Response**: `200 OK`
```json
{
  "content": [
    {
      "id": "65f1a2b3c4d5e6f7g8h9i0j3",
      "authorId": "65f1a2b3c4d5e6f7g8h9i0j1",
      "title": "My First Post",
      "content": "This is the content of my first post...",
      "media": [],
      "likeCount": 5,
      "commentsContent": [
        "Great post!",
        "Very informative"
      ]
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalElements": 1,
  "totalPages": 1,
  "last": true,
  "first": true,
  "numberOfElements": 1,
  "empty": false
}
```

---

### 7. Get Posts by Author (with Pagination)

**Endpoint**: `GET /posts/author/{{user_id}}?page=0&size=10&sort=createdAt,desc`

**Headers**:
```
Authorization: Bearer {{jwt_token}}
```

**Query Parameters**: Same as Get Feed

**Expected Response**: `200 OK`
```json
{
  "content": [
    {
      "id": "65f1a2b3c4d5e6f7g8h9i0j3",
      "title": "My First Post",
      "content": "This is the content of my first post...",
      "authorId": "65f1a2b3c4d5e6f7g8h9i0j1",
      "commentsIds": ["comment1", "comment2"],
      "likesIds": ["like1", "like2"],
      "likeCount": 2,
      "media": [],
      "createdAt": "2026-01-31T10:35:00Z"
    }
  ],
  "pageable": {...},
  "totalElements": 1,
  "totalPages": 1
}
```

---

## Media Service APIs

### Base URL: `{{base_url}}:{{media_port}}`

### Authentication Required
All Media Service endpoints require JWT token.

---

### 1. Upload Media

**Endpoint**: `POST /media/upload`

**Full URL**: `http://localhost:4070/media/upload` or `{{base_url}}:{{media_port}}/media/upload`

⚠️ **Important**: Make sure you're using port **4070** (Media Service), NOT 4060 (Notification Service)

**Headers**:
```
Authorization: Bearer {{jwt_token}}
```

**Body** (form-data):
- Key: `file` (type: File)
- Value: Select a file (image or video)

**Expected Response**: `200 OK`
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "userId": "65f1a2b3c4d5e6f7g8h9i0j1",
  "mediaType": "IMAGE",
  "mimeType": "image/jpeg",
  "originalName": "photo.jpg",
  "fileSize": 2048576,
  "storagePath": "media/user123/uuid-photo.jpg",
  "status": "READY",
  "uploadedAt": "2026-01-31T10:40:00Z"
}
```

**Test Script**:
```javascript
pm.test("Status code is 200", function () {
    pm.response.to.have.status(200);
});

pm.test("Media ID is saved", function () {
    var jsonData = pm.response.json();
    pm.environment.set("media_id", jsonData.id);
});
```

**Note**: 
- In Postman, select Body → form-data
- Add key `file` and change type to `File` from the dropdown
- Click "Select Files" and choose an image or video file

---

### 2. Get User Media (with Pagination)

**Endpoint**: `GET /media/user/?page=0&size=20&sort=uploadedAt,desc`

**Headers**:
```
Authorization: Bearer {{jwt_token}}
```

**Query Parameters**:
- `page` (optional): Page number - Default: 0
- `size` (optional): Items per page - Default: 20
- `sort` (optional): Sort field - Default: uploadedAt
  - `uploadedAt,desc` - Latest first
  - `fileSize,desc` - Largest first

**Expected Response**: `200 OK`
```json
{
  "content": [
    {
      "id": "550e8400-e29b-41d4-a716-446655440000",
      "userId": "65f1a2b3c4d5e6f7g8h9i0j1",
      "mediaType": "IMAGE",
      "mimeType": "image/jpeg",
      "originalName": "photo.jpg",
      "fileSize": 2048576,
      "storagePath": "media/user123/uuid-photo.jpg",
      "status": "READY",
      "uploadedAt": "2026-01-31T10:40:00Z"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 20
  },
  "totalElements": 1,
  "totalPages": 1
}
```

---

### 3. Get Media by ID

**Endpoint**: `GET /media/{mediaId}`

**Headers**:
```
Authorization: Bearer {{jwt_token}}
```

**Path Variables**:
- `mediaId`: UUID of the media (e.g., `550e8400-e29b-41d4-a716-446655440000`)

**Expected Response**: `200 OK`
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "userId": "65f1a2b3c4d5e6f7g8h9i0j1",
  "mediaType": "IMAGE",
  "mimeType": "image/jpeg",
  "originalName": "photo.jpg",
  "fileSize": 2048576,
  "storagePath": "media/user123/uuid-photo.jpg",
  "status": "READY",
  "uploadedAt": "2026-01-31T10:40:00Z"
}
```

---

## Analytics Service APIs

### Base URL: `{{base_url}}:{{analytics_port}}`

### No Authentication Required
Analytics endpoints are public.

---

### 1. Get Global Analytics Overview

**Endpoint**: `GET /analytics/overview`

**Headers**:
```
Content-Type: application/json
```

**Expected Response**: `200 OK`
```json
{
  "totalUsers": 150,
  "totalPosts": 450,
  "totalLikes": 2300,
  "totalComments": 890,
  "totalFollows": 550,
  "averageLikesPerPost": 5.11,
  "averageCommentsPerPost": 1.98,
  "averageFollowersPerUser": 3.67
}
```

---

## Common Issues & Solutions

### 1. **401 Unauthorized Error**

**Problem**: JWT token is missing or expired

**Solution**:
1. Login again to get a new token
2. Make sure the Authorization header is set: `Bearer {{jwt_token}}`
3. Check that the environment variable `jwt_token` is populated

---

### 2. **400 Bad Request - Validation Errors**

**Problem**: Invalid input data

**Example Response**:
```json
{
  "timestamp": "2026-01-31T10:30:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed for object='createUserRequest'. Error count: 2",
  "path": "/users/"
}
```

**Solution**:
- Check required fields are present
- Verify email format is correct
- Check string length constraints:
  - Username: 3-30 characters
  - Password: 6-100 characters
  - Bio: max 500 characters
  - Post title: 1-200 characters
  - Post content: 1-5000 characters
  - Comment content: 1-1000 characters

---

### 3. **404 Not Found**

**Problem**: Resource doesn't exist or wrong endpoint

**Solution**:
- Verify the endpoint URL is correct
- Check that IDs in path parameters exist
- Ensure services are running: `docker ps`

---

### 4. **500 Internal Server Error**

**Problem**: Server-side error

**Solution**:
- Check service logs: `docker logs <service-name>`
- Verify MongoDB, Redis, and Kafka are running
- Check if database has required data

---

### 5. **Connection Refused**

**Problem**: Service is not running

**Solution**:
```bash
# Check if services are running
docker ps

# Restart services
docker-compose restart

# Check logs
docker logs auth-service
docker logs user-service
docker logs post-service
```

---

### 6. **Pagination Not Working**

**Problem**: Query parameters not formatted correctly

**Solution**:
- Use correct format: `?page=0&size=10&sort=createdAt,desc`
- Page numbers are 0-indexed (first page = 0)
- Multiple sort parameters: `?sort=likeCount,desc&sort=createdAt,desc`

---

### 7. **Trailing Slash Error (404)**

**Problem**: Getting 404 or "No static resource" errors

**Solution**:
- ❌ Wrong: `/users/followers/` (with trailing slash)
- ✅ Correct: `/users/followers` (without trailing slash)
- Spring MVC treats URLs with trailing slashes as requests for static resources
- Always use endpoints without trailing slashes

---

## Postman Collection Import

### Create a Postman Collection

1. **Create New Collection**: Name it "Social Media Backend"
2. **Add folders**:
   - Auth Service
   - User Service
   - Post Service
   - Media Service
   - Analytics Service

3. **Add requests** following the structure above

4. **Collection Variables**:
   - Set `Authorization` at collection level
   - Type: Bearer Token
   - Token: `{{jwt_token}}`

---

## Testing Workflow Example

### Complete User Journey

1. **Register a new user**
   ```
   POST {{base_url}}:8080/auth/register
   ```

2. **Login to get JWT**
   ```
   POST {{base_url}}:8080/auth/login
   ```

3. **Create user profile**
   ```
   POST {{base_url}}:8081/users/
   ```

4. **Create a post**
   ```
   POST {{base_url}}:8082/posts/
   ```

5. **Like the post**
   ```
   POST {{base_url}}:8082/posts/{{post_id}}/like
   ```

6. **Comment on the post**
   ```
   POST {{base_url}}:8082/posts/{{post_id}}/comment
   ```

7. **View feed**
   ```
   GET {{base_url}}:8082/posts/feed?page=0&size=10
   ```

8. **Register another user** (repeat steps 1-3)

9. **Follow the first user**
   ```
   POST {{base_url}}:8081/users/follow
   ```

10. **Check analytics**
    ```
    GET {{base_url}}:4090/analytics/overview
    ```

---

## Swagger UI Alternative

Each service also has Swagger UI for interactive API testing:

- **Auth Service**: http://localhost:8080/swagger-ui.html
- **User Service**: http://localhost:8081/swagger-ui.html
- **Post Service**: http://localhost:8082/swagger-ui.html
- **Media Service**: http://localhost:4070/swagger-ui.html
- **Analytics Service**: http://localhost:4090/swagger-ui.html
- **Notification Service**: http://localhost:4060/swagger-ui.html

In Swagger UI:
1. Click **Authorize** button
2. Enter: `Bearer <your_jwt_token>`
3. Click **Authorize**
4. Test endpoints directly

---

## Tips & Best Practices

1. **Save Requests**: Save all requests in a Postman collection for reuse
2. **Use Environment Variables**: Store tokens, IDs, and URLs as variables
3. **Test Scripts**: Add test scripts to automatically save response data
4. **Pre-request Scripts**: Add scripts to generate test data
5. **Collections**: Organize requests by service
6. **Documentation**: Add descriptions to each request
7. **Response Examples**: Save example responses
8. **Folders**: Use folders to group related requests

---

## Next Steps

1. **Load Testing**: Use Postman Runner for batch testing
2. **Automated Testing**: Set up Newman for CI/CD
3. **Monitoring**: Use Postman Monitors for API health checks
4. **Mock Servers**: Create mock servers for frontend development
5. **Documentation**: Generate API documentation from Postman collection

---

## Support

For issues or questions:
- Check service logs: `docker logs <service-name>`
- Review validation rules in the code
- Check [PAGINATION_GUIDE.md](PAGINATION_GUIDE.md) for pagination details
- Verify environment variables in docker-compose.yml

Happy Testing! 🚀
