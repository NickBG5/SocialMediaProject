package com.example.socialmediaproject.service;

import com.example.socialmediaproject.dto.FollowResponse;
import com.example.socialmediaproject.entity.Follow;
import com.example.socialmediaproject.entity.User;
import com.example.socialmediaproject.repository.FollowRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final UserService userService;

    public FollowService(FollowRepository followRepository, UserService userService) {
        this.followRepository = followRepository;
        this.userService = userService;
    }

    public FollowResponse follow(Long followerId, Long followedId) {

        // If already following, return the existing follow as a response
        var existing = followRepository.findByFollowerIdAndFollowedId(followerId, followedId);
        if (existing.isPresent()) {
            Follow f = existing.get();
            return new FollowResponse(
                    f.getId(),
                    f.getFollower().getId(),
                    f.getFollowed().getId(),
                    f.getCreatedAt()
            );
        }

        // Otherwise create a new follow
        User follower = userService.getUser(followerId);
        User followed = userService.getUser(followedId);

        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowed(followed);

        Follow saved = followRepository.save(follow);

        return new FollowResponse(
                saved.getId(),
                saved.getFollower().getId(),
                saved.getFollowed().getId(),
                saved.getCreatedAt()
        );
    }

    public void unfollow(Long followerId, Long followedId) {
        followRepository.findByFollowerIdAndFollowedId(followerId, followedId)
                .ifPresent(followRepository::delete);
    }

    public List<Long> getFollowedUserIds(Long followerId) {
        return followRepository.findFollowedUserIds(followerId);
    }
}
