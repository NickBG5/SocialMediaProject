package com.example.socialmediaproject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponse {
    private Long id;
    private Long userId;
    private Long postId;
    private String content;
    private String createdAt;
}
