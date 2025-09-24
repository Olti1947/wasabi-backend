package com.sushi.wasabi.services;

import com.sushi.wasabi.dto.CreateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void createUser(CreateUserRequest createUserRequest);
}
