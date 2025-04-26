package com.backend.internalhackthon.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PromptDTO {
    @NotBlank(message = "Prompt Cannot be Blank")
//    Also Apply size of prompt here
    private String prompt;
}
