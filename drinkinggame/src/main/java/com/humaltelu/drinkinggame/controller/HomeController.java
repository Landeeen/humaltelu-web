package com.humaltelu.drinkinggame.controller;

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
