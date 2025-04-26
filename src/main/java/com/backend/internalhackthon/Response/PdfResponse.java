package com.backend.internalhackthon.Response;

import com.backend.internalhackthon.DTO.PDFDto;
import com.backend.internalhackthon.Model.PDF;
import lombok.Data;

import java.util.List;

@Data
public class PdfResponse {
private String message;
private boolean status;
private List<PDF> pdfList;


}
