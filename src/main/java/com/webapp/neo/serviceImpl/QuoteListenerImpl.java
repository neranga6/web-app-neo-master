package com.webapp.neo.serviceImpl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapp.neo.service.QuoteListenerService;
import com.webapp.neo.service.QuoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuoteListenerImpl implements QuoteListenerService {

    @Autowired
    QuoteService quoteService;

    private static final Logger logger = LoggerFactory.getLogger(QuoteListenerImpl.class);

    public String getQuoteMsg() {
        String quote = quoteService.quote();
        logger.info("Quote from home method: {}", quote);
        ObjectMapper mapper = new ObjectMapper();
        String cachedQuote = null;

        try {
            JsonNode jsonNode = mapper.readTree(quote);
            String content = jsonNode.get("content").asText();
            String author = jsonNode.get("author").asText();
            cachedQuote = "Quote: " + content + " | Author: " + author;

            logger.info("Content: {}", content);
            logger.info("Author: {}", author);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return cachedQuote;
    }
}
