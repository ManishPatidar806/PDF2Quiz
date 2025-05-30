package com.backend.internalhackthon.Model.Response;

import lombok.Data;

@Data
public class ChatResponse {
    private String message ;
    private boolean status;
    private String  response;
}
