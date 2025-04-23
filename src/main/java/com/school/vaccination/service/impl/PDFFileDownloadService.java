package com.school.vaccination.service.impl;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import com.school.vaccination.response.StudentVaccinationResponse;
import com.school.vaccination.service.FileDownloadService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service("pdfFileDownloadService")
public class PDFFileDownloadService implements FileDownloadService {

    @Override
    public void downloadFile(List<StudentVaccinationResponse> studentVaccinationResponseList, HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=vaccination_data.pdf");

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        Table table = new Table(UnitValue.createPercentArray(5)).useAllAvailableWidth();


        table.addCell(new Cell().add(new Paragraph("Student Name")));
        table.addCell(new Cell().add(new Paragraph("Mobile No")));
        table.addCell(new Cell().add(new Paragraph("Vaccinated")));
        table.addCell(new Cell().add(new Paragraph("Vaccination Date")));
        table.addCell(new Cell().add(new Paragraph("Vaccine Name")));

        for (StudentVaccinationResponse res : studentVaccinationResponseList) {
            table.addCell(new Cell().add(new Paragraph(res.getStudentName())));
            table.addCell(new Cell().add(new Paragraph(res.getStudentMobileNo())));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(res.isVaccinated()))));
            table.addCell(new Cell().add(new Paragraph(res.getVaccinationDate().toString())));
            table.addCell(new Cell().add(new Paragraph(res.getVaccineName())));
        }

        document.add(table);
        document.close();
    }
}
