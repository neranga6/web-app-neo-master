package com.webapp.neo.serviceImpl;

import com.webapp.neo.model.User;
import com.webapp.neo.repositories.UserOTPRepository;
import com.webapp.neo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserOTPRepository userOTPRepository;

    @Autowired
    public UserServiceImpl(UserOTPRepository userOTPRepository) {
        this.userOTPRepository = userOTPRepository;
    }

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    public void saveUser(String password, String email) {
        User user = new User();
        user.setPassword(password);
        user.setEmail(email);
        userOTPRepository.save(user);

    }

    public User findUser(String OTP) {
        return userOTPRepository.findByPassword(OTP);
    }

    public void deleteUser(User user) {
        userOTPRepository.delete(user);
    }

    public String generateRandomOTP() {
        final String OTP_CHARS = "0123456789";
        final int OTP_LENGTH = 6;

        SecureRandom random;
        try {
            random = SecureRandom.getInstanceStrong();
        } catch (Exception e) {
            // Handle the exception appropriately
            logger.error(e.getMessage());
            throw new RuntimeException("Failed to get SecureRandom instance");
        }

        // Generate OTP using streams
        String otp = random.ints(OTP_LENGTH, 0, OTP_CHARS.length())  // Generate OTP_LENGTH random ints between 0 and OTP_CHARS.length()
                .mapToObj(OTP_CHARS::charAt) // Map each integer to a character from OTP_CHARS
                .map(String::valueOf) // Convert char to String
                .collect(Collectors.joining()); // Join all characters into a single string

        // Clear the SecureRandom instance
        random.nextBytes(new byte[20]); // Clearing out the SecureRandom instance
        random = null; // Nullifying the instance

        return otp;
    }


    public boolean validateOTP(String otp) {
        Optional<User> abc = Optional.ofNullable(userOTPRepository.findByPassword(otp));
        return abc.isPresent();
    }
}

