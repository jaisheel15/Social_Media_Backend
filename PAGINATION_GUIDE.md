# Pagination and Sorting Guide

This document describes how pagination and sorting have been implemented across all services in the Social Media Backend.

## Overview

Pagination and sorting have been added to all endpoints that return lists of data to improve performance and user experience.

## Implemented Endpoints

### User Service

#### 1. Get Followers
- **Endpoint**: `GET /users/followers`
- **Default**: 20 items per page, sorted by `createdAt`
- **Parameters**:
  - `page` - Page number (0-indexed)
  - `size` - Number of items per page
  - `sort` - Sort field and direction (e.g., `createdAt,desc`)

**Example**:
```
GET /users/followers?page=0&size=10&sort=createdAt,desc
```

#### 2. Get Following
- **Endpoint**: `GET /users/following`
- **Default**: 20 items per page, sorted by `createdAt`
- **Parameters**: Same as Get Followers

**Example**:
```
GET /users/following?page=1&size=15&sort=username,asc
```

### Post Service

#### 1. Get Feed
- **Endpoint**: `GET /posts/feed`
- **Default**: 10 items per page, sorted by `createdAt`
- **Parameters**:
  - `page` - Page number (0-indexed)
  - `size` - Number of items per page
  - `sort` - Sort field and direction

**Example**:
```
GET /posts/feed?page=0&size=20&sort=createdAt,desc
GET /posts/feed?page=0&size=10&sort=likeCount,desc
```

#### 2. Get Posts by Author
- **Endpoint**: `GET /posts/author/{authorId}`
- **Default**: 10 items per page, sorted by `createdAt`
- **Parameters**: Same as Get Feed

**Example**:
```
GET /posts/author/123456?page=0&size=10&sort=createdAt,desc
```

### Media Service

#### 1. Get User Media
- **Endpoint**: `GET /media/user/`
- **Default**: 20 items per page, sorted by `uploadedAt`
- **Parameters**:
  - `page` - Page number (0-indexed)
  - `size` - Number of items per page
  - `sort` - Sort field and direction

**Example**:
```
GET /media/user/?page=0&size=30&sort=uploadedAt,desc
GET /media/user/?page=1&size=20&sort=fileSize,desc
```

## Response Format

All paginated endpoints return a `Page` object with the following structure:

```json
{
  "content": [...],           // Array of items
  "pageable": {
    "pageNumber": 0,          // Current page number
    "pageSize": 10,           // Items per page
    "sort": {
      "sorted": true,
      "unsorted": false,
      "empty": false
    },
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "totalElements": 150,       // Total number of items
  "totalPages": 15,           // Total number of pages
  "last": false,              // Is this the last page?
  "first": true,              // Is this the first page?
  "size": 10,                 // Items per page
  "number": 0,                // Current page number
  "sort": {
    "sorted": true,
    "unsorted": false,
    "empty": false
  },
  "numberOfElements": 10,     // Number of items in current page
  "empty": false              // Is the page empty?
}
```

## Sorting Options

### Available Sort Fields

**User Service (UserDetail)**:
- `createdAt` - Account creation date
- `username` - Username alphabetically
- `followersCount` - Number of followers
- `followingCount` - Number of following

**Post Service (Post)**:
- `createdAt` - Post creation date
- `likeCount` - Number of likes
- `title` - Post title alphabetically

**Media Service (Media)**:
- `uploadedAt` - Upload date
- `fileSize` - File size
- `originalName` - File name alphabetically

### Sort Direction

- `asc` - Ascending order (default)
- `desc` - Descending order

### Multiple Sort Fields

You can sort by multiple fields:
```
GET /posts/feed?sort=likeCount,desc&sort=createdAt,desc
```

## Default Values

All pagination parameters are optional. If not provided, the following defaults are used:

| Service | Endpoint | Default Size | Default Sort |
|---------|----------|--------------|--------------|
| User Service | /followers | 20 | createdAt |
| User Service | /following | 20 | createdAt |
| Post Service | /feed | 10 | createdAt |
| Post Service | /author/{id} | 10 | createdAt |
| Media Service | /user/ | 20 | uploadedAt |

## Caching

Pagination results are cached with keys that include page number and size:
- `userId_pageNumber_pageSize`
- `authorId_pageNumber_pageSize`

This ensures efficient caching while maintaining accurate pagination.

## Best Practices

1. **Use appropriate page sizes**: Too large can impact performance, too small increases API calls
2. **Always specify sort direction**: For consistent ordering across pages
3. **Handle empty pages gracefully**: Check the `empty` field in the response
4. **Use totalElements for UI**: Display total count to users
5. **Cache results on client**: Reduce server load for frequently accessed pages

## Examples

### Pagination Flow

```javascript
// First page
GET /posts/feed?page=0&size=10&sort=createdAt,desc

// Next page
GET /posts/feed?page=1&size=10&sort=createdAt,desc

// Check if more pages exist using totalPages from response
```

### Popular Posts Feed

```javascript
// Get most liked posts
GET /posts/feed?page=0&size=20&sort=likeCount,desc&sort=createdAt,desc
```

### Recent User Media

```javascript
// Get latest uploads
GET /media/user/?page=0&size=30&sort=uploadedAt,desc
```

### User's Followers by Join Date

```javascript
// Get newest followers first
GET /users/followers?page=0&size=20&sort=createdAt,desc
```

## Implementation Details

- All repositories extend `MongoRepository` which provides pagination support
- Controllers use `@PageableDefault` annotation to set defaults
- Services use `Pageable` parameter and return `Page<T>` objects
- Cache keys include pagination parameters for accurate caching
- Spring Data automatically handles pagination queries to MongoDB
