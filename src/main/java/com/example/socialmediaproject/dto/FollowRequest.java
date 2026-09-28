package com.example.socialmediaproject.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowRequest {
    @NotNull(message = "Follower ID is required")
    private Long followerId;

    @NotNull(message = "Followed ID is required")
    private Long followedId;
}
