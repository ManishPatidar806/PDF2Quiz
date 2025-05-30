package com.backend.internalhackthon.Controller;

import com.backend.internalhackthon.DTO.PDFDto;
import com.backend.internalhackthon.Model.Entity.PDF;
import com.backend.internalhackthon.Model.Response.CommonResponse;
import com.backend.internalhackthon.Model.Response.PdfResponse;
import com.backend.internalhackthon.Service.QuestionService;
import com.backend.internalhackthon.Service.UserDetail;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("/api/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("/upload")
    public ResponseEntity<CommonResponse> uploadPdf(
            @AuthenticationPrincipal UserDetail userDetail,
            @RequestParam("pdf") MultipartFile pdf) throws IOException {
        CommonResponse response = new CommonResponse();

        PDFDto pdfDto = new PDFDto();
        pdfDto.setPdfData(pdf.getBytes());
        pdfDto.setUserId(1);
        pdfDto.setPdfName(pdf.getOriginalFilename());
        // System.out.println("pdf dats is "+pdfDto);
        questionService.uploadPdf(pdfDto);
        response.setMessage("PDF uploaded Successfully");
        response.setStatus(true);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/getallpdf")
    public ResponseEntity<PdfResponse> getPdf(
            @AuthenticationPrincipal UserDetail userDetail,
            @RequestParam(defaultValue = "0") Long id) throws IOException {
        PdfResponse response = new PdfResponse();
        List<PDF> pdfs = questionService.getAllPdf(id);
        response.setPdfList(pdfs);

        response.setMessage("PDF GETS Successfully");
        response.setStatus(true);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping("/generateQuestionfromPdf")
    public ResponseEntity<JsonNode> generateQuestion(@RequestParam("file") MultipartFile file) {
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
            String extractedText = questionService.extractTextFromFile(path.toFile());

            // Send the text to AI API
            JsonNode aiResponse = questionService.sendToAI(extractedText);

            return ResponseEntity.ok(aiResponse);
        } catch (io.jsonwebtoken.io.IOException | java.io.IOException e) {
            System.err.println("Fetch Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

}
