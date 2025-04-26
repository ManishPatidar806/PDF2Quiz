package com.backend.internalhackthon.Helper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

public class OllamaService {
    private static final String OLLAMA_API_URL = "http://localhost:11434/api/generate";

    public String generateQuestions(String syllabusText) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("input", syllabusText);  // Adjust the key as per Ollama’s API specs

        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(OLLAMA_API_URL, request, String.class);

        return response.getBody();
    }
}


