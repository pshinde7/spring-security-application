package com.dreamcoder.client.controller;


import com.dreamcoder.client.entity.User;
import com.dreamcoder.client.event.RegistrationCompleteEvent;
import com.dreamcoder.client.event.publisher.RegistrationCompleteEventPublisher;
import com.dreamcoder.client.model.UserModel;
import com.dreamcoder.client.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private RegistrationCompleteEventPublisher publisher;


    @GetMapping("/hello")
    public String hello() {
        return "Hello, Dream Coder ...!";
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody UserModel userModel, final HttpServletRequest request) {

        User user = userService.registerUser(userModel);
        publisher.publishEvent(user, request);

        return "Success";

    }

    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token) {
        String result = userService.verifyRegistration(token);
        String response = result.equals("valid") ? "Succesfully Verified" : result;
        return response;
    }

}
