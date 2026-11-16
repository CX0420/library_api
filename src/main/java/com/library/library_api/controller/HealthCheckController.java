package com.library.library_api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    private static final Logger logger =
            LoggerFactory.getLogger(HealthCheckController.class);

    public HealthCheckController() {
        logger.info("HealthCheckController initialized!");
    }

    @GetMapping("/healthcheck/test")
    public String test() {
        logger.info("Health check API called!");

        return "Library API is running successfully!";
    }
}
