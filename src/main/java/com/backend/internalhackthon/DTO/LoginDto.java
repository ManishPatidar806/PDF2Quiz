package com.backend.internalhackthon.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginDto {
    @NotBlank(message = "Email must be Valid")
private String email;
    @NotBlank(message = "Password must be Valid")
private String password;
    @NotBlank(message = "Role must be Valid")
    private String role;
}
