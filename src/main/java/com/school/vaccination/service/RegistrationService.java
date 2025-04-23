package com.school.vaccination.service;

import com.school.vaccination.enums.FileDownloadType;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public interface RegistrationService {

    void registerVaccination(String studentIdentifier, String driveIdentifier);

    void download(FileDownloadType fileDownloadType, HttpServletResponse response) throws Exception;
}
