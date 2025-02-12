package com.webapp.neo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class QuoteController {

    private static final Logger logger = LoggerFactory.getLogger(QuoteController.class);
    private static final String Json = "static/json/about.json";
    private static final String listJson = "static/json/list.json";

    @GetMapping("/api/about")
    private ResponseEntity<String> getStringAboutResponseEntity() {
        return getStringResponseEntity();
    }

    @GetMapping("/api/experience")
    private ResponseEntity<String> getStringListResponseEntity() {
        Path path = Paths.get(listJson);
        return getStringResponseEntity(path);
    }

    private ResponseEntity<String> getStringResponseEntity() {
        Path path = Paths.get(QuoteController.Json);
        return getStringResponseEntity(path);
    }

    private ResponseEntity<String> getStringResponseEntity(Path path) {
        ClassPathResource resource = new ClassPathResource(path.toString());
        try {
            // Read the content of the file
            InputStream inputStream = resource.getInputStream();
            byte[] bytes = inputStream.readAllBytes();
            String jsonData = new String(bytes, StandardCharsets.UTF_8);
            logger.info(jsonData);
            // Return the content as a response
            return ResponseEntity.ok(jsonData);
        } catch (IOException e) {
            // If an error occurs while reading the file, return a 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error reading the file");
        }
    }
}
