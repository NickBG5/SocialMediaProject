package com.example.socialmediaproject.service;

import com.example.socialmediaproject.dto.CreatePostRequest;
import com.example.socialmediaproject.dto.PostResponse;
import com.example.socialmediaproject.entity.Post;
import com.example.socialmediaproject.entity.User;
import com.example.socialmediaproject.exception.PostNotFoundException;
import com.example.socialmediaproject.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public PostResponse createPost(CreatePostRequest request) {
        User user = userService.getUser(request.getUserId());

        Post post = new Post();
        post.setUser(user);
        post.setContent(request.getContent());
        postRepository.save(post);

        return toPostResponse(post);
    }

    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));

        return toPostResponse(post);
    }

    public Post getPostEntity(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    public Page<PostResponse> getPostsByUser(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return postRepository.findByUserId(userId, pageable)
                .map(this::toPostResponse);
    }

    private PostResponse toPostResponse(Post post) {
        PostResponse response = new PostResponse();
        response.setId(post.getId());
        response.setUserId(post.getUser().getId());
        response.setContent(post.getContent());
        response.setCreatedAt(post.getCreatedAt().toString());
        return response;
    }
}
