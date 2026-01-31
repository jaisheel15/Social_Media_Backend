# File Tree: Social_Media_Backend

**Generated:** 1/31/2026, 4:52:54 AM
**Root Path:** `/home/jaisheel/Desktop/Project/Social_Media_Backend`

```
├── 📁 analytics_service
│   ├── 📁 .mvn
│   │   └── 📁 wrapper
│   │       └── 📄 maven-wrapper.properties
│   ├── 📁 src
│   │   ├── 📁 main
│   │   │   ├── 📁 java
│   │   │   │   └── 📁 com
│   │   │   │       └── 📁 example
│   │   │   │           └── 📁 analytics_service
│   │   │   │               ├── 📁 controller
│   │   │   │               │   └── ☕ AnalyticsController.java
│   │   │   │               ├── 📁 dto
│   │   │   │               │   └── ☕ GlobalMetricResponse.java
│   │   │   │               ├── 📁 events
│   │   │   │               │   └── ☕ UserEventListener.java
│   │   │   │               ├── 📁 model
│   │   │   │               │   ├── ☕ GlobalMetrics.java
│   │   │   │               │   ├── ☕ UserCreatedEvent.java
│   │   │   │               │   ├── ☕ UserFollowEvent.java
│   │   │   │               │   ├── ☕ UserLikeEvent.java
│   │   │   │               │   └── ☕ UserPostEvent.java
│   │   │   │               ├── 📁 service
│   │   │   │               │   └── ☕ GlobalAnalyticsService.java
│   │   │   │               ├── 📁 topic
│   │   │   │               │   └── ☕ UserTopic.java
│   │   │   │               └── ☕ AnalyticsServiceApplication.java
│   │   │   └── 📁 resources
│   │   │       ├── 📁 static
│   │   │       ├── 📁 templates
│   │   │       └── 📄 application.properties
│   │   └── 📁 test
│   │       └── 📁 java
│   │           └── 📁 com
│   │               └── 📁 example
│   │                   └── 📁 analytics_service
│   │                       └── ☕ AnalyticsServiceApplicationTests.java
│   ├── ⚙️ .gitattributes
│   ├── ⚙️ .gitignore
│   ├── 🐳 Dockerfile
│   ├── 📝 HELP.md
│   ├── 📄 mvnw
│   ├── 📄 mvnw.cmd
│   └── ⚙️ pom.xml
├── 📁 auth_service
│   ├── 📁 .mvn
│   │   └── 📁 wrapper
│   │       └── 📄 maven-wrapper.properties
│   ├── 📁 src
│   │   ├── 📁 main
│   │   │   ├── 📁 java
│   │   │   │   └── 📁 com
│   │   │   │       └── 📁 example
│   │   │   │           └── 📁 auth_service
│   │   │   │               ├── 📁 Controller
│   │   │   │               │   └── ☕ AuthController.java
│   │   │   │               ├── 📁 config
│   │   │   │               │   ├── ☕ JwtFilter.java
│   │   │   │               │   ├── ☕ RateLimitFilter.java
│   │   │   │               │   └── ☕ SecurityConfig.java
│   │   │   │               ├── 📁 dto
│   │   │   │               │   ├── ☕ AuthResponse.java
│   │   │   │               │   ├── ☕ LoginRequest.java
│   │   │   │               │   └── ☕ RegisterRequest.java
│   │   │   │               ├── 📁 model
│   │   │   │               │   ├── ☕ AuthUser.java
│   │   │   │               │   └── ☕ Role.java
│   │   │   │               ├── 📁 repository
│   │   │   │               │   └── ☕ AuthUserRepository.java
│   │   │   │               ├── 📁 service
│   │   │   │               │   ├── ☕ AuthService.java
│   │   │   │               │   └── ☕ AuthUserService.java
│   │   │   │               ├── 📁 util
│   │   │   │               │   └── ☕ JwtUtil.java
│   │   │   │               └── ☕ AuthServiceApplication.java
│   │   │   └── 📁 resources
│   │   │       ├── 📁 static
│   │   │       ├── 📁 templates
│   │   │       └── 📄 application.properties
│   │   └── 📁 test
│   │       └── 📁 java
│   │           └── 📁 com
│   │               └── 📁 example
│   │                   └── 📁 auth_service
│   │                       └── ☕ AuthServiceApplicationTests.java
│   ├── ⚙️ .gitattributes
│   ├── ⚙️ .gitignore
│   ├── 🐳 Dockerfile
│   ├── 📝 HELP.md
│   ├── 📄 mvnw
│   ├── 📄 mvnw.cmd
│   └── ⚙️ pom.xml
├── 📁 media_service
│   ├── 📁 .mvn
│   │   └── 📁 wrapper
│   │       └── 📄 maven-wrapper.properties
│   ├── 📁 src
│   │   ├── 📁 main
│   │   │   ├── 📁 java
│   │   │   │   └── 📁 com
│   │   │   │       └── 📁 example
│   │   │   │           └── 📁 media_service
│   │   │   │               ├── 📁 config
│   │   │   │               │   ├── ☕ JwtFilter.java
│   │   │   │               │   ├── ☕ MinioConfig.java
│   │   │   │               │   ├── ☕ MinioProperties.java
│   │   │   │               │   └── ☕ SecurityConfig.java
│   │   │   │               ├── 📁 controller
│   │   │   │               │   └── ☕ MediaController.java
│   │   │   │               ├── 📁 dto
│   │   │   │               │   └── ☕ UploadResponse.java
│   │   │   │               ├── 📁 model
│   │   │   │               │   └── ☕ Media.java
│   │   │   │               ├── 📁 repository
│   │   │   │               │   └── ☕ MediaRepository.java
│   │   │   │               ├── 📁 service
│   │   │   │               │   └── ☕ MediaService.java
│   │   │   │               ├── 📁 util
│   │   │   │               │   └── ☕ JwtUtil.java
│   │   │   │               └── ☕ MediaServiceApplication.java
│   │   │   └── 📁 resources
│   │   │       ├── 📁 static
│   │   │       ├── 📁 templates
│   │   │       └── 📄 application.properties
│   │   └── 📁 test
│   │       └── 📁 java
│   │           └── 📁 com
│   │               └── 📁 example
│   │                   └── 📁 media_service
│   │                       └── ☕ MediaServiceApplicationTests.java
│   ├── ⚙️ .gitattributes
│   ├── ⚙️ .gitignore
│   ├── 🐳 Dockerfile
│   ├── 📝 HELP.md
│   ├── 📄 mvnw
│   ├── 📄 mvnw.cmd
│   └── ⚙️ pom.xml
├── 📁 notification_service
│   ├── 📁 .mvn
│   │   └── 📁 wrapper
│   │       └── 📄 maven-wrapper.properties
│   ├── 📁 src
│   │   ├── 📁 main
│   │   │   ├── 📁 java
│   │   │   │   └── 📁 com
│   │   │   │       └── 📁 example
│   │   │   │           └── 📁 notification_service
│   │   │   │               ├── 📁 config
│   │   │   │               │   └── ☕ RestTemplateConfig.java
│   │   │   │               ├── 📁 controller
│   │   │   │               │   └── ☕ EmailController.java
│   │   │   │               ├── 📁 dto
│   │   │   │               │   ├── ☕ UserFollowedEvent.java
│   │   │   │               │   └── ☕ UserInfoRequest.java
│   │   │   │               ├── 📁 model
│   │   │   │               │   ├── ☕ PostCommentedEvent.java
│   │   │   │               │   ├── ☕ PostLikedEvent.java
│   │   │   │               │   └── ☕ UserInfo.java
│   │   │   │               ├── 📁 repository
│   │   │   │               │   └── ☕ UserInfoRepository.java
│   │   │   │               ├── 📁 service
│   │   │   │               │   ├── ☕ EmailService.java
│   │   │   │               │   ├── ☕ FollowNotificationListener.java
│   │   │   │               │   ├── ☕ PostNotificationListener.java
│   │   │   │               │   ├── ☕ UserDetailsClient.java
│   │   │   │               │   └── ☕ UserInfoListener.java
│   │   │   │               └── ☕ NotificationServiceApplication.java
│   │   │   └── 📁 resources
│   │   │       ├── 📁 static
│   │   │       ├── 📁 templates
│   │   │       └── 📄 application.properties
│   │   └── 📁 test
│   │       └── 📁 java
│   │           └── 📁 com
│   │               └── 📁 example
│   │                   └── 📁 notification_service
│   │                       └── ☕ NotificationServiceApplicationTests.java
│   ├── ⚙️ .gitattributes
│   ├── ⚙️ .gitignore
│   ├── 🐳 Dockerfile
│   ├── 📝 HELP.md
│   ├── 📄 mvnw
│   ├── 📄 mvnw.cmd
│   └── ⚙️ pom.xml
├── 📁 post_service
│   ├── 📁 .mvn
│   │   └── 📁 wrapper
│   │       └── 📄 maven-wrapper.properties
│   ├── 📁 src
│   │   ├── 📁 main
│   │   │   ├── 📁 java
│   │   │   │   └── 📁 com
│   │   │   │       └── 📁 example
│   │   │   │           └── 📁 post_service
│   │   │   │               ├── 📁 config
│   │   │   │               │   ├── ☕ JwtFilter.java
│   │   │   │               │   ├── ☕ RateLimitFilter.java
│   │   │   │               │   └── ☕ SecurityConfig.java
│   │   │   │               ├── 📁 controller
│   │   │   │               │   └── ☕ PostController.java
│   │   │   │               ├── 📁 dto
│   │   │   │               │   ├── ☕ CreateCommentRequest.java
│   │   │   │               │   ├── ☕ CreatePostRequest.java
│   │   │   │               │   ├── ☕ FeedRequest.java
│   │   │   │               │   ├── ☕ FeedResponse.java
│   │   │   │               │   └── ☕ UserLikeRequest.java
│   │   │   │               ├── 📁 event
│   │   │   │               │   └── ☕ PostEventProducer.java
│   │   │   │               ├── 📁 model
│   │   │   │               │   ├── ☕ Comments.java
│   │   │   │               │   ├── ☕ Likes.java
│   │   │   │               │   ├── ☕ Post.java
│   │   │   │               │   ├── ☕ PostCommentEvent.java
│   │   │   │               │   ├── ☕ PostCreatedEvent.java
│   │   │   │               │   └── ☕ PostLikeEvent.java
│   │   │   │               ├── 📁 repository
│   │   │   │               │   ├── ☕ CommentRepository.java
│   │   │   │               │   ├── ☕ LikesRepository.java
│   │   │   │               │   └── ☕ PostRepository.java
│   │   │   │               ├── 📁 service
│   │   │   │               │   └── ☕ PostService.java
│   │   │   │               ├── 📁 topic
│   │   │   │               │   └── ☕ PostTopic.java
│   │   │   │               ├── 📁 util
│   │   │   │               │   └── ☕ JwtUtil.java
│   │   │   │               └── ☕ PostServiceApplication.java
│   │   │   └── 📁 resources
│   │   │       ├── 📁 static
│   │   │       ├── 📁 templates
│   │   │       └── 📄 application.properties
│   │   └── 📁 test
│   │       └── 📁 java
│   │           └── 📁 com
│   │               └── 📁 example
│   │                   └── 📁 post_service
│   │                       └── ☕ PostServiceApplicationTests.java
│   ├── ⚙️ .gitattributes
│   ├── ⚙️ .gitignore
│   ├── 🐳 Dockerfile
│   ├── 📝 HELP.md
│   ├── 📄 mvnw
│   ├── 📄 mvnw.cmd
│   └── ⚙️ pom.xml
├── 📁 user_service
│   ├── 📁 .mvn
│   │   └── 📁 wrapper
│   │       └── 📄 maven-wrapper.properties
│   ├── 📁 src
│   │   ├── 📁 main
│   │   │   ├── 📁 java
│   │   │   │   └── 📁 com
│   │   │   │       └── 📁 example
│   │   │   │           └── 📁 user_service
│   │   │   │               ├── 📁 Controller
│   │   │   │               │   ├── ☕ InternalController.java
│   │   │   │               │   └── ☕ UserController.java
│   │   │   │               ├── 📁 config
│   │   │   │               │   ├── ☕ JwtFilter.java
│   │   │   │               │   ├── ☕ RateLimitFilter.java
│   │   │   │               │   └── ☕ SecurityConfig.java
│   │   │   │               ├── 📁 dto
│   │   │   │               │   ├── ☕ CreateUserRequest.java
│   │   │   │               │   ├── ☕ FollowUserRequest.java
│   │   │   │               │   ├── ☕ UserInfoRequest.java
│   │   │   │               │   └── ☕ UserUpdateRequest.java
│   │   │   │               ├── 📁 event
│   │   │   │               │   └── ☕ UserEventProducer.java
│   │   │   │               ├── 📁 model
│   │   │   │               │   ├── ☕ Follow.java
│   │   │   │               │   ├── ☕ UserCreatedEvent.java
│   │   │   │               │   ├── ☕ UserDetail.java
│   │   │   │               │   └── ☕ UserFollowEvent.java
│   │   │   │               ├── 📁 repository
│   │   │   │               │   ├── ☕ FollowRepository.java
│   │   │   │               │   └── ☕ UserRepository.java
│   │   │   │               ├── 📁 service
│   │   │   │               │   └── ☕ UserDetailsService.java
│   │   │   │               ├── 📁 topic
│   │   │   │               │   ├── ☕ PostTopic.java
│   │   │   │               │   └── ☕ UserTopic.java
│   │   │   │               ├── 📁 util
│   │   │   │               │   └── ☕ JwtUtil.java
│   │   │   │               └── ☕ UserServiceApplication.java
│   │   │   └── 📁 resources
│   │   │       ├── 📁 static
│   │   │       ├── 📁 templates
│   │   │       └── 📄 application.properties
│   │   └── 📁 test
│   │       └── 📁 java
│   │           └── 📁 com
│   │               └── 📁 example
│   │                   └── 📁 user_service
│   │                       └── ☕ UserServiceApplicationTests.java
│   ├── ⚙️ .gitattributes
│   ├── ⚙️ .gitignore
│   ├── 🐳 Dockerfile
│   ├── 📝 HELP.md
│   ├── 📄 mvnw
│   ├── 📄 mvnw.cmd
│   └── ⚙️ pom.xml
├── ⚙️ .gitignore
└── ⚙️ docker-compose.yml
```

---
*Generated by FileTree Pro Extension*