package com.backend.internalhackthon.Model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class PDF {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long userId;

    private String pdfName;
    @Lob
    private byte[] pdfData;
}
