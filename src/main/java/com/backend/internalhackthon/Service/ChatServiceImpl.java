package com.backend.internalhackthon.Service;

import lombok.SneakyThrows;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService{

    @Autowired
    private OllamaChatModel chatModel;

    //    @Autowired
//    public ChatController(OllamaChatModel chatModel) {
//        this.chatModel = chatModel;
//    }

    @SneakyThrows
    public String chatResponse(String prompt)  {

        return chatModel.call(prompt);
    }


}
