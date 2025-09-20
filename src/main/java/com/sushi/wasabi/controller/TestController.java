package com.sushi.wasabi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @GetMapping(path = "/test")
    public String testController(){
    log.info("This controller was hit");
    return "I was hit";
}

}
