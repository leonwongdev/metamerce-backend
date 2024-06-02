package dev.leonwong.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    // A function to return a welcome message
    @GetMapping("/")
    public String home() {
        return "Welcome to the home page!";
    }
}
