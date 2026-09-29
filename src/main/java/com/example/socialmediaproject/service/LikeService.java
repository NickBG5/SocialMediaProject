package com.example.socialmediaproject.service;

import com.example.socialmediaproject.dto.LikeResponse;
import com.example.socialmediaproject.entity.User;
import com.example.socialmediaproject.entity.Like;
import com.example.socialmediaproject.entity.Post;
import com.example.socialmediaproject.repository.LikeRepository;
import com.example.socialmediaproject.service.UserService;
import com.example.socialmediaproject.service.PostService;
import org.springframework.stereotype.Service;


@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserService userService;
    private final PostService postService;

    public LikeService(LikeRepository likeRepository,
                       UserService userService,
                       PostService postService) {
        this.likeRepository = likeRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public LikeResponse likePost(Long userId, Long postId) {
        if (likeRepository.findByUserIdAndPostId(userId, postId).isPresent()) {
            // Already liked — return the existing like as a response
            Like existing = likeRepository.findByUserIdAndPostId(userId, postId).get();
            return new LikeResponse(
                    existing.getId(),
                    existing.getUser().getId(),
                    existing.getPost().getId(),
                    existing.getCreatedAt()
            );
        }

        User user = userService.getUser(userId);
        Post post = postService.getPostEntity(postId);

        Like like = new Like();
        like.setUser(user);
        like.setPost(post);

        Like saved = likeRepository.save(like);

        return new LikeResponse(
                saved.getId(),
                saved.getUser().getId(),
                saved.getPost().getId(),
                saved.getCreatedAt()
        );
    }

    public void unlikePost(Long userId, Long postId) {
        likeRepository.findByUserIdAndPostId(userId, postId)
                .ifPresent(likeRepository::delete);
    }

    public long countLikes(Long postId) {
        return likeRepository.countByPostId(postId);
    }

}
