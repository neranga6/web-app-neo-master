package com.webapp.neo.serviceImpl;


import com.webapp.neo.model.PasswordForm;
import com.webapp.neo.repositories.PasswordFormRepository;
import com.webapp.neo.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class DataServiceImpl implements DataService {
    @Autowired
    PasswordFormRepository passwordFormRepository;
    @Async
    public void savePassword(PasswordForm passwordForm) {
        passwordFormRepository.save(passwordForm);
    }
}
