package com.example.socialmediaproject.service;

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

    public void likePost(Long userId, Long postId) {
        if (likeRepository.findByUserIdAndPostId(userId, postId).isPresent()) {
            return; // already liked
        }

        User user = userService.getUser(userId);
        Post post = postService.getPostEntity(postId);

        Like like = new Like();
        like.setUser(user);
        like.setPost(post);

        likeRepository.save(like);
    }

    public void unlikePost(Long userId, Long postId) {
        likeRepository.findByUserIdAndPostId(userId, postId)
                .ifPresent(likeRepository::delete);
    }

    public long countLikes(Long postId) {
        return likeRepository.countByPostId(postId);
    }
}
