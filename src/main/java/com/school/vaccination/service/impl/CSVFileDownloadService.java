package com.school.vaccination.service.impl;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.school.vaccination.response.StudentVaccinationResponse;
import com.school.vaccination.service.FileDownloadService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service("csvFileDownloadService")
public class CSVFileDownloadService implements FileDownloadService {
    @Override
    public void downloadFile(List<StudentVaccinationResponse> studentVaccinationResponseList, HttpServletResponse response) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=vaccination_data.csv");

        StatefulBeanToCsv<StudentVaccinationResponse> writer = new StatefulBeanToCsvBuilder<StudentVaccinationResponse>(
                response.getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(',')
                .build();

        writer.write(studentVaccinationResponseList);
    }
}
