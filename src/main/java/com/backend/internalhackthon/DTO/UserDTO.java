package com.backend.internalhackthon.DTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDTO {

    @NotBlank(message = "Name must be Valid")
    private String name;
    @NotBlank(message = "Username must be Valid")
    private String username;
    @NotBlank(message = "Email must be Valid")
    private String email;
    @NotBlank(message = "Password must be Valid")
    private String password;
    @NotBlank(message = "Role must be Valid")
    private String role;



}
