package com.webapp.neo.controller;

import com.webapp.neo.service.QuoteListenerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EventListenerController {

    @Autowired
    QuoteListenerService quoteListenerService;
    private static final Logger logger = LoggerFactory.getLogger(EventListenerController.class);
    
    private static String eventMessage = null;
    private static String quoteMessages = null;

    @KafkaListener(topics = "quote-msg")
    public void receive(@Payload String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        eventMessage = message;
        logger.info("lasting was received{}", eventMessage);
    }

    @GetMapping("/api/quote")
    public ResponseEntity<String> getQuote() {

        try {
            // Ensure eventMessage is checked before calling the service
            if (eventMessage != null) {
                quoteMessages = quoteListenerService.getQuoteMsg();
                logger.info("msg was triggered: {}", quoteMessages);
            }
            // Check if quoteMessages is null or empty
            if (quoteMessages != null && !quoteMessages.isEmpty()) {
                return ResponseEntity.ok(quoteMessages);
            } else {
                logger.warn("No quote message found.");
                return ResponseEntity.notFound().build(); // Return 404 if no quote found
            }
        } catch (Exception e) {
            logger.error("Error while retrieving quote: {}", e.getMessage(), e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the quote. Please try again later.");
        }
    }

}
