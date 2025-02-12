package com.webapp.neo.repositories;

import com.webapp.neo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface UserOTPRepository extends JpaRepository<User, Long> {
    User findByPassword(String OTP);

}
