package com.sushi.wasabi.controller;

import com.sushi.wasabi.dto.CreateUserRequest;
import com.sushi.wasabi.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class TestController {

    private final UserService userService;

    @GetMapping(path = "/test")
    public String testController(){
    log.info("This controller was hit");
    return "I was hit";
}

}
