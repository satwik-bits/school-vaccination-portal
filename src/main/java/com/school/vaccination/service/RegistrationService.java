package com.school.vaccination.service;

import org.springframework.stereotype.Service;

@Service
public interface RegistrationService {

    void registerVaccination(String studentIdentifier, String driveIdentifier);
}
