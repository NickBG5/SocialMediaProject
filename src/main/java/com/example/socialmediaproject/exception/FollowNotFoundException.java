package com.example.socialmediaproject.exception;

public class FollowNotFoundException extends RuntimeException {
    public FollowNotFoundException(Long id) {
        super("Follow relationship not found: " + id);
    }
}
