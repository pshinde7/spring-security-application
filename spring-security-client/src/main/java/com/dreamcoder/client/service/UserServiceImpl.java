package com.dreamcoder.client.service;

import com.dreamcoder.client.entity.User;
import com.dreamcoder.client.entity.VerificationToken;
import com.dreamcoder.client.model.UserModel;
import com.dreamcoder.client.repository.UserRepository;
import com.dreamcoder.client.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Calendar;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    /**
     * @param userModel
     * @return
     */
    @Override
    public User registerUser(UserModel userModel) {

        User user = User.builder().firstName(userModel.getFirstName())
                .lastName(userModel.getLastName())
                .email(userModel.getEmail())
                .role("USER")
                .password(passwordEncoder.encode(userModel.getPassword()).toString())
                .build();
        userRepository.save(user);
        return user;
    }

    /**
     * @param token
     * @param user
     */
    @Override
    public void saveVerificationTokenForUser(String token, User user) {

        VerificationToken verificationToken = new VerificationToken(user, token);
        verificationTokenRepository.save(verificationToken);

    }

    /**
     * @param token
     * @return
     */
    @Override
    public String verifyRegistration(String token) {

        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);

        String response = ObjectUtils.isEmpty(verificationToken) ? "invalid" : checkExpirationTime(verificationToken);

        return response;
    }

    private String checkExpirationTime(VerificationToken verificationToken) {

        User user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();
        String response = verificationToken.getExpiration().getTime() - calendar.getTime().getTime() <= 0 ? deleteToken(verificationToken) : "valid";

        return response;

    }

    private String deleteToken(VerificationToken verificationToken) {

        verificationTokenRepository.delete(verificationToken);
        return "token expired";
    }
}
