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
public class FollowResponse {
    private Long id;
    private Long followerId;
    private Long followeeId;
    private Instant createdAt;
}
