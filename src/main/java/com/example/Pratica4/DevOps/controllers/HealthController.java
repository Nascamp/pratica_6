package com.example.Pratica4.DevOps.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/")
    public String home() {
        return "Aplicação OK";
    }

    @GetMapping("/health")
    public String health() {
        return "healthy";
    }
}
