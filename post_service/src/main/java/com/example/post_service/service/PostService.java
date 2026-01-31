kwpackage com.example.post_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.post_service.dto.CreateCommentRequest;
import com.example.post_service.dto.CreatePostRequest;
import com.example.post_service.dto.FeedResponse;
import com.example.post_service.event.PostEventProducer;
import com.example.post_service.model.Comments;
import com.example.post_service.model.Likes;
import com.example.post_service.model.Post;
import com.example.post_service.repository.CommentRepository;
import com.example.post_service.repository.LikesRepository;
import com.example.post_service.repository.PostRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final LikesRepository likesRepository;
    private final CommentRepository commentRepository;
    private final PostEventProducer postEventProducer;

    @Cacheable(value = "posts", key = "#id")
    public Post getPostById(String id) {
        return postRepository.findById(id).orElse(null);
    }

    @CacheEvict(value = "feed", allEntries = true)
    public Post createPost(CreatePostRequest request) {
        Post post = Post.builder()
                .title(request.getTitle())
                .media(request.getMedia())
                .content(request.getContent())
                .authorId(request.getAuthorId())
                .commentsIds(new ArrayList<>())
                .likesIds(new ArrayList<>())
                .build();

        Post savedPost = postRepository.save(post);
        postEventProducer.publishPostCreateEvent(savedPost.getAuthorId(), savedPost.getId());
        return savedPost;
    }

    @Caching(evict = {
            @CacheEvict(value = "posts", key = "#id"),
            @CacheEvict(value = "feed", allEntries = true)
    })
    public void deletePost(String id) {
        postRepository.deleteById(id);
    }

    @Caching(evict = {
            @CacheEvict(value = "posts", key = "#postId"),
            @CacheEvict(value = "feed", allEntries = true)
    })
    public Likes likePost(String userId, String postId) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            return null;
        }

        if (post.getLikesIds() == null) {
            post.setLikesIds(new ArrayList<>());
        }

        Optional<Likes> existing = likesRepository.findByUserIdAndPostId(userId, postId);
        if (existing.isPresent()) {
            return existing.get();
        }

        Likes saved = likesRepository.save(Likes.builder()
                .postId(postId)
                .userId(userId)
                .build());

        post.getLikesIds().add(saved.getId());
        post.setLikeCount(post.getLikeCount() + 1);
        postRepository.save(post);
        postEventProducer.publishPostLikeEvent(postId, post.getAuthorId(), userId);
        return saved;
    }

    @Caching(evict = {
            @CacheEvict(value = "posts", key = "#postId"),
            @CacheEvict(value = "feed", allEntries = true)
    })
    public void commentPost(String postId, CreateCommentRequest request) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            return;
        }

        if (post.getCommentsIds() == null) {
            post.setCommentsIds(new ArrayList<>());
        }

        Comments comment = commentRepository.save(Comments.builder()
                .postId(postId)
                .userId(request.getUserId())
                .content(request.getContent())
                .build());

        post.getCommentsIds().add(comment.getId());

        postRepository.save(post);

        postEventProducer.publishPostCommentEvent(postId, post.getAuthorId(), request.getUserId());
    }

    // Note: Caching disabled for Page objects due to Redis deserialization
    // complexity
    public Page<FeedResponse> getFeed(String userId, Pageable pageable) {
        Page<Post> postsPage = postRepository.findAll(pageable);

        List<FeedResponse> feedResponses = new ArrayList<>();
        for (Post post : postsPage.getContent()) {
            List<String> commentsContent = new ArrayList<>();
            if (post.getCommentsIds() != null) {
                for (String commentId : post.getCommentsIds()) {
                    Comments comment = commentRepository.findById(commentId).orElse(null);
                    if (comment != null) {
                        commentsContent.add(comment.getContent());
                    }
                }
            }

            FeedResponse feedResponse = new FeedResponse(
                    post.getId(),
                    post.getAuthorId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getMedia(),
                    post.getLikeCount(),
                    commentsContent);

            feedResponses.add(feedResponse);
        }
        return new org.springframework.data.domain.PageImpl<>(feedResponses, pageable, postsPage.getTotalElements());
    }

    // Note: Caching disabled for Page objects due to Redis deserialization
    // complexity
    public Page<Post> getPostsByAuthor(String authorId, Pageable pageable) {
        return postRepository.findByAuthorId(authorId, pageable);
    }

}
