package com.webapp.neo.controller;


import com.webapp.neo.model.PasswordForm;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;


@Controller

public class LoginController {

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/submit-form")
    public RedirectView submitLoginForm(Model model, @RequestParam("password") String password) {
        PasswordForm passwordForm = new PasswordForm();
        passwordForm.setPassword(password);
        model.addAttribute("PasswordForm",passwordForm);
       // dataService.savePassword(passwordForm);
        return new RedirectView("/pdf");
    }

}