package com.backend.internalhackthon.Model.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String mobile;

    @Column(unique = true)
    private String email;

    private String password;

    private String username;
    private String role;
    private String institute;
    private String city;
    private String state;
    private String country;
    private String linkedUrl;
    private String bio;





}
