package com.backend.internalhackthon.Controller;

import com.backend.internalhackthon.Service.SyllabusQuestionService;
import com.fasterxml.jackson.databind.JsonNode;
import io.jsonwebtoken.io.IOException;
import io.swagger.v3.core.util.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Null;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/api/syllabus")
public class SyllabusQuestionController {


    private final SyllabusQuestionService service;

    public SyllabusQuestionController(SyllabusQuestionService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ResponseEntity<JsonNode> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // Generate file name
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path path = Paths.get("uploads/" + fileName);

            // Check if file already exists
            if (Files.exists(path)) {
                System.out.println("File already exists. Using existing file.");
            } else {
                // Save the file locally if it doesn't exist
                Files.createDirectories(path.getParent());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File uploaded successfully: " + path.toAbsolutePath());
            }

            // Process the file to extract text
            String extractedText = service.extractTextFromFile(path.toFile());

            // Send the text to AI API
            JsonNode aiResponse = service.sendToAI(extractedText);

            return ResponseEntity.ok(aiResponse);
        } catch (IOException | java.io.IOException e) {
            System.err.println("Upload Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}
