package com.backend.internalhackthon.Service;

import org.springframework.stereotype.Service;

@Service
public interface ChatService {
    public String chatResponse(String prompt);
}
