package com.backend.internalhackthon.Service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Primary
@Service
public interface SyllabusQuestionService {
    public JsonNode sendToAI(String syllabusText);
    public String extractTextFromFile(File file) throws IOException;
}
