package com.example.socialmediaproject.service;

import com.example.socialmediaproject.dto.FeedResponse;
import com.example.socialmediaproject.entity.Post;
import com.example.socialmediaproject.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedService {

    private final FollowService followService;
    private final PostRepository postRepository;

    public FeedService(FollowService followService, PostRepository postRepository) {
        this.followService = followService;
        this.postRepository = postRepository;
    }

    public Page<FeedResponse> getFeed(Long userId, int page, int size) {
        List<Long> followedIds = followService.getFollowedUserIds(userId);

        if (followedIds.isEmpty()) {
            return Page.empty();
        }

        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findFeedPosts(followedIds, pageable)
                .map(this::toFeedResponse);
    }

    private FeedResponse toFeedResponse(Post post) {
        FeedResponse response = new FeedResponse();
        response.setPostId(post.getId());
        response.setUserId(post.getUser().getId());
        response.setContent(post.getContent());
        response.setCreatedAt(post.getCreatedAt().toString());
        return response;
    }
}
