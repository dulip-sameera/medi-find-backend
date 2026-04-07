package dev.dulipsameera.auth_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "Auth Service is LIVE! Please provide credentials.";
    }

}
