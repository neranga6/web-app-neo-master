package com.webapp.neo.service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Path;
import java.util.function.BiConsumer;

@FunctionalInterface
public interface PDFService {
    void pdfViewer(Path path, HttpServletResponse response) throws Exception;
}