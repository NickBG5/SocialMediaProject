package com.example.socialmediaproject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostResponse {
    private Long id;
    private Long userId;
    private String content;
    private String createdAt;
}
