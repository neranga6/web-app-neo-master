package com.webapp.neo.service;

import com.webapp.neo.model.User;

public interface UserService {


    void saveUser(String password, String email);

    boolean validateOTP(String otp);

    String generateRandomOTP();

    User findUser(String OTP);

    void deleteUser(User user);
}
