package com.tfg.eldest.frontend.pdfs;

import com.itextpdf.html2pdf.HtmlConverter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

@Service
public class PdfService {

    public byte[] generatePdf(String templatePath, Map<String, String> placeholders) throws IOException {
        // Read the HTML template as a string
        String htmlContent = new String(Files.readAllBytes(Paths.get(templatePath)), StandardCharsets.UTF_8);

        // Replace placeholders with actual values
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            htmlContent = htmlContent.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            // Convert the HTML content to PDF and write it to the output stream
            HtmlConverter.convertToPdf(htmlContent, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Failed to generate PDF", e);
        }

        return outputStream.toByteArray();
    }
}
