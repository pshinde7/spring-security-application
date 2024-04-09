package com.dreamcoder.client.service;

import com.dreamcoder.client.entity.User;
import com.dreamcoder.client.model.UserModel;
import org.springframework.stereotype.Component;

@Component
public interface UserService {
    User registerUser(UserModel userModel);

    void saveVerificationTokenForUser(String token, User user);

    String verifyRegistration(String token);
}
