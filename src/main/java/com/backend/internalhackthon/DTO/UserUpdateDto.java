package com.backend.internalhackthon.DTO;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserUpdateDto {
    @NotBlank(message = "Name must be Valid")
    private String name;
    @NotBlank(message = "Mobile Number must be Valid")
    private String mobile;
    @NotBlank(message = "Username must be Valid")
    private String username;
    @NotBlank(message = "Role must be Valid")
    private String role;
    @NotBlank(message = "Institute must be Valid")
    private String institute;
    @NotBlank(message = "City must be Valid")
    private String city;
    @NotBlank(message = "State must be Valid")
    private String state;
    @NotBlank(message = "Country must be Valid")
    private String country;

    private String linkedUrl;

    private String bio;


}
