package com.backend.internalhackthon.Model.Response;

import lombok.Data;

import java.util.List;

@Data
public class QuestionResponse {

    private String message;
    private boolean status;
    private List<Object> list;

}
