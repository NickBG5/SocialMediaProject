package com.example.socialmediaproject.service;

import com.example.socialmediaproject.dto.CommentResponse;
import com.example.socialmediaproject.dto.CreateCommentRequest;
import com.example.socialmediaproject.entity.Comment;
import com.example.socialmediaproject.entity.Post;
import com.example.socialmediaproject.entity.User;
import com.example.socialmediaproject.repository.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    public CommentService(CommentRepository commentRepository,
                          UserService userService,
                          PostService postService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public CommentResponse addComment(CreateCommentRequest request) {
        User user = userService.getUser(request.getUserId());
        Post post = postService.getPostEntity(request.getPostId());

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setContent(request.getContent());
        commentRepository.save(comment);

        return toCommentResponse(comment);
    }

    public Page<CommentResponse> getComments(Long postId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return commentRepository.findByPost_IdOrderByCreatedAtDesc(postId, pageable)
                .map(this::toCommentResponse);
    }

    private CommentResponse toCommentResponse(Comment comment) {
        CommentResponse response = new CommentResponse();
        response.setId(comment.getId());
        response.setUserId(comment.getUser().getId());
        response.setPostId(comment.getPost().getId());
        response.setContent(comment.getContent());
        response.setCreatedAt(comment.getCreatedAt().toString());
        return response;
    }
}
