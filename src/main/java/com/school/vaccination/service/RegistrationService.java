package com.school.vaccination.service;

import com.school.vaccination.enums.FileDownloadType;
import com.school.vaccination.response.StudentVaccinationResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface RegistrationService {

    void registerVaccination(String studentIdentifier, String driveIdentifier);

    void download(FileDownloadType fileDownloadType, HttpServletResponse response) throws Exception;

    Page<StudentVaccinationResponse> display(int pageNo, int pageSize);
}
