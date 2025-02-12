package com.webapp.neo.controller;
import com.webapp.neo.model.ResumeForm;
import com.webapp.neo.model.User;
import com.webapp.neo.service.ResumeEmailService;
import com.webapp.neo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class ResumeController {
    Logger logger = LoggerFactory.getLogger(ResumeController.class);
    private final ResumeEmailService resumeEmailService;
    private final UserService userService;

    private static final String WORD_FOLDER = "static/words";
    private static final String PDF_FOLDER = "static/words/";

    public ResumeController(ResumeEmailService resumeEmailService, UserService userService) {
        this.resumeEmailService = resumeEmailService;
        this.userService = userService;
    }

    @GetMapping(value = "/resume-page")
    public ModelAndView showResumeForm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("resume");
        return modelAndView;
    }

    @PostMapping("/resume-form")
    public RedirectView sendResumeForm(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("email") String email, Model model, RedirectAttributes redirectAttributes) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("resume");
        model.addAttribute("resumeFrom", new ResumeForm());
        redirectAttributes.addAttribute("email", email);

        String pa = userService.generateRandomOTP();
        String subject1 = "One Time Passcode";
        String content = "Your One Time Passcode (OTP) is shown below. To access the resume , please enter it exactly as shown. Note the One Time Passcode (OTP) is case-sensitive:\n" + pa;

        userService.saveUser(pa, email);
        resumeEmailService.sendEmailWithOTP(email, subject1, content);
        return new RedirectView("/success-form");
    }

    @GetMapping(value = "/success-form")
    public ModelAndView showSuccessForm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("success");
        return modelAndView;
    }

    @RequestMapping(value = "/OTP-form", method = RequestMethod.GET)
    public RedirectView submitOTP(@RequestParam("OTP") String OTP) {
        try {
            boolean isOTPValid = userService.validateOTP(OTP);

            if (isOTPValid) {
                User user = userService.findUser(OTP);

                if (user != null) {
                    String userEmail = user.getEmail();
                    String subject = "Resume-Email";
                    String path = WORD_FOLDER + "/Neo_Urapola.pdf";
                    String content = "\n Neranga Urapola's\n Resume \n";
                    String alert = "The Resume was downloaded by\n" + userEmail;
                    String alertEmail = "neranga@neotechsys.info";
                    String alertSubject = "Resume-Downloaded-Alert";

                    // Send the email
                    resumeEmailService.sendEmailWithAttachment(userEmail, subject, content, path);
                    resumeEmailService.sendAlertEmail(alertEmail, alertSubject, alert);

                    // Delete user data after the operation is finished
                    userService.deleteUser(user);

                    return new RedirectView("/");
                }
            }
        } catch (Exception e) {
           logger.error(String.valueOf(e));

        }

        return new RedirectView("/success-form");
    }

    @GetMapping("/download/resume-form")
    public ResponseEntity<Resource> downloadResume() throws IOException {

        Path path = Paths.get(PDF_FOLDER + "Neo_Urapola.pdf");
        ClassPathResource resource = new ClassPathResource(path.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Neo_Urapola.pdf\"");

        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(resource);
    }

}
