package com.example.socialmediaproject.controller;

import com.example.socialmediaproject.dto.LikeRequest;
import com.example.socialmediaproject.dto.LikeResponse;
import com.example.socialmediaproject.service.LikeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/like")
    public ResponseEntity<LikeResponse> likePost(@Valid @RequestBody LikeRequest request) {
        LikeResponse response = likeService.likePost(request.getUserId(), request.getPostId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/unlike")
    public ResponseEntity<Void> unlikePost(@Valid @RequestBody LikeRequest request) {
        likeService.unlikePost(request.getUserId(), request.getPostId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/count/{postId}")
    public ResponseEntity<Long> countLikes(@PathVariable Long postId) {
        return ResponseEntity.ok(likeService.countLikes(postId));
    }
}
