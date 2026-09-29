package com.example.socialmediaproject.controller;

import com.example.socialmediaproject.dto.FollowRequest;
import com.example.socialmediaproject.dto.FollowResponse;
import com.example.socialmediaproject.service.FollowService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/follow")
public class FollowController {

    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping("/follow")
    public ResponseEntity<FollowResponse> follow(@RequestBody FollowRequest request) {
        FollowResponse response = followService.follow(request.getFollowerId(), request.getFollowedId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/unfollow")
    public ResponseEntity<Void> unfollow(@Valid @RequestBody FollowRequest request) {
        followService.unfollow(request.getFollowerId(), request.getFollowedId());
        return ResponseEntity.ok().build();
    }
}
