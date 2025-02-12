package com.webapp.neo.serviceImpl;


import com.webapp.neo.exceptions.WriterException;
import com.webapp.neo.service.PDFService;
import org.apache.pdfbox.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

@Service
public class PDFServiceImpl implements PDFService {
    Logger logger = LoggerFactory.getLogger(PDFServiceImpl.class);

    private final PDFService pdfViewer = (path, response) -> {
        try (InputStream inputStream = new ClassPathResource(path.toString()).getInputStream()) {
            response.setContentType(MediaType.APPLICATION_PDF_VALUE);

            // Set the response headers for inline display
            response.setHeader("Content-Disposition", "inline; filename=file.pdf");
            response.setHeader("Cache-Control", "no-cache");

            // Copy the PDF file's input stream to the response's output stream
            IOUtils.copy(inputStream, response.getOutputStream());

        } catch (IOException e) {
            // Custom WriterException if there is an issue with file reading or PDF creation
            logger.error("Error creating PDF viewer: {}", e.getMessage(), e);
            throw new WriterException("Error reading the PDF file: " + e.getMessage(), e, 1003);
        } catch (Exception e) {
            // Catch any other unexpected errors and throw a custom exception
            logger.error("Unexpected error: {}", e.getMessage(), e);
            throw new WriterException("Unexpected error while creating the PDF viewer", e, 1004);
        }
    };

    @Override
    public void pdfViewer(Path path, HttpServletResponse response) throws Exception {
        try {
            pdfViewer.pdfViewer(path, response);
        } catch (Exception e) {
            logger.error("PDF service failed: {}", e.getMessage(), e);
            // Propagate the custom exception upwards if needed
            throw e;
        }
    }
}

