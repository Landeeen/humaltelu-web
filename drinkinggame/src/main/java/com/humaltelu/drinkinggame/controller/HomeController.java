package com.humaltelu.drinkinggame.controller;

import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "HELLO, Home";
    }

    @GetMapping("/secured")
    public String secured() {
        return "HELLO, Secured";
    }

   
    
}
