package com.webapp.neo.message;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapp.neo.service.QuoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuoteEventPublisher {

    private static final Logger logger = LoggerFactory.getLogger(QuoteEventPublisher.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    @Scheduled(fixedRate = 86400000)
    @PostMapping("/api/publishQuote")
    public void publishQuote() {
        String cachedQuote = "msgPublish";
        // Assuming you have a Quote class to represent the quote data
        try {
            String message = String.format("Quote { %s } was created", cachedQuote);
            kafkaTemplate.send("Quote-msg", "Quote  {" + cachedQuote + "} was created");
            logger.info("Sent Kafka message: {}", message);
        } catch (Exception e) {
            // Handle Kafka sending failure
            logger.error("Error sending Kafka message: {}", e.getMessage(), e);
            ResponseEntity.status(500).body("Error sending message to Kafka.");

        }
        ResponseEntity.ok(cachedQuote);
    }
}
