package com.webapp.neo.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
public class QRCodeController {

    @GetMapping("/qrcode")
    public ResponseEntity<byte[]> generateQrCode() {
        try {
            String link = "https://neotechsys.info/resume-page";

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BitMatrix bitMatrix = new QRCodeWriter().encode(link, BarcodeFormat.QR_CODE, 200, 200,
                    java.util.Collections.singletonMap(EncodeHintType.MARGIN, 0));
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            byte[] qrCodeBytes = outputStream.toByteArray();

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(qrCodeBytes);
        } catch (IOException | WriterException e) {
            // Handle exceptions properly
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}