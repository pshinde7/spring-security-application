package com.dreamcoder.client.controller;


import com.dreamcoder.client.entity.User;
import com.dreamcoder.client.entity.VerificationToken;
import com.dreamcoder.client.event.publisher.RegistrationCompleteEventPublisher;
import com.dreamcoder.client.model.UserModel;
import com.dreamcoder.client.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
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

    @GetMapping("/resendverifytoken")
    public String resendVerificationToken(@RequestParam("oldtoken") String oldToken, HttpServletRequest request) {
        VerificationToken verificationToken = userService.generateNewVerificationTone(oldToken);
        User user = verificationToken.getUser();
        resendVerificationTokenMail(user, applicationUrl(request), verificationToken.getToken());
        return "Verification Link Sent .....!";
    }

    private void resendVerificationTokenMail(User user, String applicationUrl, String token) {

        String url = applicationUrl + "/verifyRegistration?token=" + token;

        log.info("Click the link to verify your account : {}", url);

    }

    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token) {
        String result = userService.verifyRegistration(token);
        return result.equals("valid") ? "Successfully Verified" : result;
    }

    private String applicationUrl(HttpServletRequest request) {

        return "http://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath();
    }

}
