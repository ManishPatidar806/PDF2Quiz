package com.backend.internalhackthon.Model.Response;

import com.backend.internalhackthon.Model.Entity.PDF;
import lombok.Data;

import java.util.List;

@Data
public class PdfResponse {
private String message;
private boolean status;
private List<PDF> pdfList;


}
