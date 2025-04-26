package com.backend.internalhackthon.DTO;

import jakarta.persistence.*;
import lombok.Data;

@Data
public class PDFDto {

    private long userId;

    private String pdfName;
    @Lob
    private byte[] pdfData;
}
