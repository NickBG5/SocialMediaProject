package com.example.socialmediaproject.service;

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

    public void follow(Long followerId, Long followedId) {
        if (followRepository.findByFollowerIdAndFollowedId(followerId, followedId).isPresent()) {
            return; // already following
        }

        User follower = userService.getUser(followerId);
        User followed = userService.getUser(followedId);

        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowed(followed);

        followRepository.save(follow);
    }

    public void unfollow(Long followerId, Long followedId) {
        followRepository.findByFollowerIdAndFollowedId(followerId, followedId)
                .ifPresent(followRepository::delete);
    }

    public List<Long> getFollowedUserIds(Long followerId) {
        return followRepository.findFollowedUserIds(followerId);
    }
}
