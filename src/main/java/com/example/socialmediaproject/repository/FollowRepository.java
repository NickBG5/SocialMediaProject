package com.example.socialmediaproject.repository;

import com.example.socialmediaproject.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> findByFollowerIdAndFollowedId(Long followerId, Long followedId);

    @Query("SELECT f.followed.id FROM Follow f WHERE f.follower.id = :followerId")
    List<Long> findFollowedUserIds(Long followerId);
}
