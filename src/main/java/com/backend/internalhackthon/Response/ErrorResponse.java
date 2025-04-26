package com.backend.internalhackthon.Response;

import lombok.Data;

@Data
public class ErrorResponse {
    private String message;
    private boolean status;
}
