package com.backend.internalhackthon.Response;

import lombok.Data;

import java.util.Map;

@Data
public class InvalidExceptionResponse {

private boolean Status;
private Map<String ,String> message;


}
