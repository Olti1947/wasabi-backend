package com.sushi.wasabi.services.impl;

import com.sushi.wasabi.dto.CreateUserRequest;
import com.sushi.wasabi.entity.User;
import com.sushi.wasabi.repository.UserRepository;
import com.sushi.wasabi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void createUser(CreateUserRequest createUserRequest) {
        User newUser = User.builder()
                            .email(createUserRequest.getEmail())
                            .username(createUserRequest.getUsername())
                            .password(createUserRequest.getPassword())
                            .build();

        userRepository.save(newUser);

    }
}
