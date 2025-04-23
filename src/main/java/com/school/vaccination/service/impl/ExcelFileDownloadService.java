package com.school.vaccination.service.impl;

import com.school.vaccination.response.StudentVaccinationResponse;
import com.school.vaccination.service.FileDownloadService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service("excelFileDownloadService")
public class ExcelFileDownloadService implements FileDownloadService {

    @Override
    public void downloadFile(List<StudentVaccinationResponse> studentVaccinationResponseList, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=vaccination_data.xlsx");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Vaccination Data");

        Row header = sheet.createRow(0);
        String[] columns = {"Student Name", "Mobile No", "Vaccinated", "Vaccination Date", "Vaccine Name"};

        for (int i = 0; i < columns.length; i++) {
            header.createCell(i).setCellValue(columns[i]);
        }

        int rowIndex = 1;
        for (StudentVaccinationResponse res : studentVaccinationResponseList) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(res.getStudentName());
            row.createCell(1).setCellValue(res.getStudentMobileNo());
            row.createCell(2).setCellValue(res.isVaccinated());
            row.createCell(3).setCellValue(res.getVaccinationDate().toString());
            row.createCell(4).setCellValue(res.getVaccineName());
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
