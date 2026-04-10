package com.papis.gymtracker.controller;

import com.papis.gymtracker.model.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/v1")
public class TestController {

    @GetMapping("/hello")
    public String hello(@AuthenticationPrincipal User user){
        return "Hello " + user.getDisplayName() + " You're logged in!";
    }
}
