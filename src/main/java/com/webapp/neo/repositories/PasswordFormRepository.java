package com.webapp.neo.repositories;

import com.webapp.neo.model.PasswordForm;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PasswordFormRepository extends JpaRepository<PasswordForm, Long> {


}
