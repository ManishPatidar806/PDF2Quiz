package com.backend.internalhackthon.DTO;

import com.backend.internalhackthon.Model.Enum.DifficultyLevel;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class QuestionDTO {
    @NotBlank(message = "Field must be Valid")
    private String typeOfQuestion;
    @NotBlank(message = "Filed must be Valid")
    private int numberOfQuestion;
    @NotBlank(message = "Filed must be Valid")
    private DifficultyLevel level;
    @NotBlank(message = "Filed must be Valid")
    private int time;

}
