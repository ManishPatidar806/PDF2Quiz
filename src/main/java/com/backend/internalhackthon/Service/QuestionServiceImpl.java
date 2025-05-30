package com.backend.internalhackthon.Service;

//import com.backend.internalhackthon.Config.WebClientConfig;
import com.backend.internalhackthon.DTO.PDFDto;
import com.backend.internalhackthon.DTO.Question;
import com.backend.internalhackthon.DTO.QuestionDTO;
import com.backend.internalhackthon.Exception.CommonException;
import com.backend.internalhackthon.Model.Entity.PDF;
import com.backend.internalhackthon.Repository.PDFRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.modelmapper.ModelMapper;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private ModelMapper mapper;


    @Autowired
    private OllamaChatModel chatModel;

@Autowired
private PDFRepository pdfRepository;

//        @Autowired
//        private WebClientConfig webClientConfig;

//        public Mono<String> fetchData() {
//            return webClientConfig.get()
//                    .uri("https://api.example.com/data")
//                    .retrieve()
//                    .bodyToMono(String.class);
//        }

    @Override
    public List<Question> getQuestion(MultipartFile pdf, QuestionDTO questionDTO) {
        return null;
    }

    @Override
    public PDFDto uploadPdf(PDFDto pdfDto) {
        PDF pdf = new PDF();
        pdf.setPdfName(pdfDto.getPdfName());
        pdf.setPdfData(pdfDto.getPdfData());
        pdfRepository.save(pdf);
        return pdfDto;
    }

    @SneakyThrows
    public List<PDF> getAllPdf (Long id){
       List<PDF> pdfs = pdfRepository.findAllByUserId(id);
       if(pdfs.isEmpty()){
           throw new CommonException("PDF Not Found!");
       }
       return pdfs;
    }





//    Generate question from pdf --> Main Feature

    @SneakyThrows
    public String extractTextFromFile(File file)  {
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
    public JsonNode sendToAI(String questionText) {
        String prompt = "You are an expert data extractor. Your ONLY task is to read the provided text and extract potential questions, classify them by type, estimate their difficulty and confidence, and extract the answer for each question. You MUST respond with valid JSON data ONLY. Do not include any introductory or concluding text, explanations, or any characters outside of the valid JSON structure. Your response MUST be a JSON object with a single key, \"questions\", whose value is a list of JSON objects. Each inner JSON object must have the keys: \"question\", \"type\", \"predicted_difficulty\", \"confidence\", and \"answer\".\n" +
                "\n" +
                "For each extracted question:\n" +
                "* **\"question\"**: The exact text of the question or a fill-in-the-blank question (replace 1-2 key words with \"____\").\n" +
                "* **\"type\"**: \"short_answer\" or \"fill_in_the_blank\".\n" +
                "* **\"predicted_difficulty\"**: \"easy\", \"moderate\", or \"hard\".\n" +
                "* **\"confidence\"**: A number between 0 and 1.\n" +
                "* **\"answer\"**: The answer derived directly from the text.\n" +
                "\n" +
                "**Input Text:** " + questionText + "\n" +
                "\n" +
                "**Example of the REQUIRED output format:**\n" +
                "\n" +
                "```json\n" +
                "{\n" +
                "  \"questions\": [\n" +
                "    {\n" +
                "      \"question\": \"with ____\",\n" +
                "      \"type\": \"fill_in_the_blank\",\n" +
                "      \"predicted_difficulty\": \"moderate\",\n" +
                "      \"confidence\": 0.65,\n" +
                "      \"answer\": \"example answer\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"question\": \"What is the capital of ____?\",\n" +
                "      \"type\": \"short_answer\",\n" +
                "      \"predicted_difficulty\": \"easy\",\n" +
                "      \"confidence\": 0.9,\n" +
                "      \"answer\": \"example capital\"\n" +
                "    }\n" +
                "  ]\n" +
                "}\n" +
                "```\n" +
                "\n" +
                "Respond **ONLY** with the JSON data.";

        String aiResponse = chatModel.call(prompt);
        ObjectMapper objectMapper = new ObjectMapper();

        // Attempt to parse, with error handling and potential cleaning
        try {
            return objectMapper.readTree(aiResponse);
        } catch (IOException e) {
            System.err.println("Error parsing initial JSON response: " + e.getMessage());
            System.err.println("Raw AI Response: " + aiResponse);

            // Try to clean the response by trimming whitespace
            String trimmedResponse = aiResponse.trim();
            try {
                return objectMapper.readTree(trimmedResponse);
            } catch (IOException e2) {
                System.err.println("Error parsing trimmed JSON response: " + e2.getMessage());
                System.err.println("Trimmed AI Response: " + trimmedResponse);

                // Add more sophisticated cleaning if needed, like removing leading text
                // based on common patterns you observe in the AI's incorrect responses.
                // For example, if it often starts with a specific phrase:
                // String cleanedResponse = aiResponse.replaceFirst("^SomeLeadingPhrase\\s*", "");
                // try {
                //     return objectMapper.readTree(cleanedResponse);
                // } catch (IOException e3) {
                //     System.err.println("Error parsing further cleaned response: " + e3.getMessage());
                //     System.err.println("Further Cleaned Response: " + cleanedResponse);
                // }

                return null; // Or throw a more specific exception
            }}}


}
