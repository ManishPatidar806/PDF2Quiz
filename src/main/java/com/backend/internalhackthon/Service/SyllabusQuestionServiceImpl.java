package com.backend.internalhackthon.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class SyllabusQuestionServiceImpl implements SyllabusQuestionService {

    @Autowired
    private OllamaChatModel chatModel;

    public String extractTextFromFile(File file) throws java.io.IOException {
        if (file.getName().endsWith(".pdf")) {
            return extractTextFromPDF(file);
        } else if (file.getName().endsWith(".docx")) {
            return extractTextFromDocx(file);
        } else {
            throw new IllegalArgumentException("Unsupported file format");
        }
    }

    private String extractTextFromPDF(File file) throws java.io.IOException {
        PDDocument document = PDDocument.load(file);
        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(document);
        document.close();
        return text;
    }

    private String extractTextFromDocx(File file) throws IOException, FileNotFoundException {
        try (FileInputStream fis = new FileInputStream(file); XWPFDocument document = new XWPFDocument(fis)) {
            StringBuilder text = new StringBuilder();
            for (XWPFParagraph para : document.getParagraphs()) {
                text.append(para.getText()).append("\n");
            }
            return text.toString();
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    public JsonNode sendToAI(String syllabusText) {
        String prompt = "Generate 2 multiple-choice questions (MCQs) with four options (A, B, C, D) for each question. Clearly mark the correct answer for each question.\n\n"
                + "The response must be in **JSON format only** with no extra text. Use the following structure:\n"
                + "{\n"
                + "  \"questions\": [\n"
                + "    {\n"
                + "      \"question\": \"[Insert question here]\",\n"
                + "      \"options\": {\n"
                + "        \"A\": \"[Option A]\",\n"
                + "        \"B\": \"[Option B]\",\n"
                + "        \"C\": \"[Option C]\",\n"
                + "        \"D\": \"[Option D]\"\n"
                + "      },\n"
                + "      \"correct_answer\": \"[A/B/C/D]\"\n"
                + "    }\n"
                + "    // Add more questions as needed\n"
                + "  ]\n"
                + "}\n\n"
                + "Syllabus:\n" + syllabusText;
//        String prompt = "Generate 5 MCQs in JSON format with this structure:\n" +
//                "{ \"questions\": [\n" +
//                "   { \"question\": \"[Insert question here]\", \"options\": { \"A\": \"Option A\", \"B\": \"Option B\", \"C\": \"Option C\", \"D\": \"Option D\" }, \"correct_answer\": \"A\" },\n" +
//                "   ...\n" +
//                "] }";

        String aiResponse = chatModel.call(prompt);
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readTree(aiResponse);
    }


}
