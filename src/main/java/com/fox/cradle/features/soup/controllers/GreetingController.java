package com.fox.cradle.features.soup.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GreetingController {

    @GetMapping("/greet")
    public String greetPage(Model model) {
        model.addAttribute("message", "Hello from the Spring Boot server!");
        return "greeting";
    }
}