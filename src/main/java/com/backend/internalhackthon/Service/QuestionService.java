package com.backend.internalhackthon.Service;

import com.backend.internalhackthon.DTO.PDFDto;
import com.backend.internalhackthon.DTO.Question;
import com.backend.internalhackthon.DTO.QuestionDTO;
import com.backend.internalhackthon.Model.PDF;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
public interface QuestionService {
    public List<Question> getQuestion(MultipartFile pdf , QuestionDTO questionDTO);

    public PDFDto uploadPdf(PDFDto pdfDto);

    public List<PDF> getAllPdf (Long id);

    public String extractTextFromFile(File file);

    public JsonNode sendToAI(String syllabusText);




}
