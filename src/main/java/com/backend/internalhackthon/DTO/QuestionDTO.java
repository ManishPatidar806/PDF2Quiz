package com.backend.internalhackthon.DTO;

import com.backend.internalhackthon.Enum.DifficultyLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

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
