package com.webapp.neo.controller;

import com.webapp.neo.service.PDFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class PDFController {
    PDFService pdfService;
    private static final String WORD_FOLDER = "static/words/";

    @Autowired
    PDFController(PDFService pdfService) {
        this.pdfService = pdfService;

    }

    @GetMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public void viewPdf(@RequestParam(name = "page", defaultValue = "0") int pageNumber,
                        HttpServletResponse response) throws Exception {
        Path path = Paths.get(WORD_FOLDER + "Neo_Urapola1.pdf");
        pdfService.pdfViewer(path, response);

    }

    @GetMapping("/download/resume")
    public ResponseEntity<Resource> downloadResume() throws IOException {
        Path path = Paths.get(WORD_FOLDER + "Neo_Urapola1.pdf");
        ClassPathResource resource = new ClassPathResource(path.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Neo_Urapola1.pdf\"");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
}
