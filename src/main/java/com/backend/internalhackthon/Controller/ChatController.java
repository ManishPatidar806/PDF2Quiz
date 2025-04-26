package com.backend.internalhackthon.Controller;

import com.backend.internalhackthon.DTO.PromptDTO;
import com.backend.internalhackthon.Response.ChatResponse;
import com.backend.internalhackthon.Service.ChatService;
import jakarta.validation.Valid;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chatbot")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/generate")
    public ResponseEntity<ChatResponse> generate(@RequestBody @Valid PromptDTO promptDTO) {
        ChatResponse chatResponse = new ChatResponse();
        String response = chatService.chatResponse(promptDTO.getPrompt());
        chatResponse.setMessage("Response Generated Successfully");
        chatResponse.setStatus(true);
        chatResponse.setResponse(response);
        return new ResponseEntity<>(chatResponse, HttpStatus.OK);
    }

    //// This is not working Api
    // @GetMapping("/ai/generateStream")
    // public Flux<ChatResponse> generateStream(@RequestParam(value = "message",
    //// defaultValue = "Tell me a joke") String message) {
    // Prompt prompt = new Prompt(new UserMessage(message));
    // return this.chatModel.stream(prompt);
    // }

}