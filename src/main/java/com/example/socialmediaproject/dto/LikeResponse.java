package com.example.socialmediaproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikeResponse {
    private Long id;
    private Long userId;
    private Long postId;
    private Instant createdAt;
}
