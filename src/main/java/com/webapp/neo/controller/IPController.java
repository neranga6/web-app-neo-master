package com.webapp.neo.controller;

import com.webapp.neo.model.IPDetails;
import com.webapp.neo.service.IPDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IPController {
    IPDetailsService ipDetailsService;

    IPController(IPDetailsService ipDetailsService) {
        this.ipDetailsService = ipDetailsService;
    }

    @RequestMapping("/")
    public ModelAndView home(HttpServletRequest request, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/api/ip-details")
    @ResponseBody
    public ResponseEntity<Object> successView(HttpServletRequest request) {
        String ip =  ipDetailsService.ipGrab(request);
        ipDetailsService.saveIPDetails(ip);
        IPDetails myIP = new IPDetails();
        myIP.setIP(ip);
        String userIP = myIP.getIP();
     return ResponseEntity.ok(userIP);
    }


    @GetMapping("/service")
    public ModelAndView uploadResume() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("contact");
        return modelAndView;
    }

}
