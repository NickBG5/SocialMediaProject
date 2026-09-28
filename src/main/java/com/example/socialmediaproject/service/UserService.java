package com.example.socialmediaproject.service;

import com.example.socialmediaproject.dto.CreateUserRequest;
import com.example.socialmediaproject.dto.UserResponse;
import com.example.socialmediaproject.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import com.example.socialmediaproject.entity.User;
import com.example.socialmediaproject.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse createUser(CreateUserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        userRepository.save(user);

        return toUserResponse(user);
    }

    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
    private UserResponse toUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setCreatedAt(user.getCreatedAt().toString());
        return response;
    }
}
