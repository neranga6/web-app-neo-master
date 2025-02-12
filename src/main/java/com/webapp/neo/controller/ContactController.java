package com.webapp.neo.controller;


import com.webapp.neo.model.ContactForm;
import com.webapp.neo.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ContactController {
    private final EmailService emailService;

    @Autowired
    public ContactController(EmailService emailService) {
        this.emailService = emailService;

    }

    @PostMapping("/contact")
    public RedirectView submitContactForm(@ModelAttribute ContactForm contactForm) {
        String to = "neranga@neotechsys.info";
        String subject1 = "subject: " + contactForm.getSubject();
        String content = "Name: " + contactForm.getfirstName() + " " + contactForm.getlastName() + "\n"
                + "Email: " + contactForm.getEmail() + "\n"
                + "Message: " + contactForm.getMessage();
        emailService.sendEmail(to, subject1, content);
        // Redirect to success page
        return new RedirectView("/api/success");
    }

    @GetMapping("/api/success")
    @ResponseBody
    public ResponseEntity<Object> successView() {
        String quote = "Message Successfully sent";
        return ResponseEntity.ok(quote);
    }
}
