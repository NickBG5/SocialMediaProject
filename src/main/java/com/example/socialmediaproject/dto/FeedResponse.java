package com.example.socialmediaproject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedResponse {
    private Long postId;
    private Long userId;
    private String content;
    private String createdAt;
}
