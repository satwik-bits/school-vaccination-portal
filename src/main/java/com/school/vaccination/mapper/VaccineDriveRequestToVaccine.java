package com.school.vaccination.mapper;

import com.school.vaccination.entity.VaccineDrive;
import com.school.vaccination.request.VaccinationDriveRequest;

public class VaccineDriveRequestToVaccine {

    public static VaccineDrive toEntity(VaccinationDriveRequest request) {
        VaccineDrive vaccineDrive = new VaccineDrive();
        vaccineDrive.setDriveIdentifier(request.getIdentifier());
        vaccineDrive.setLocation(request.getLocation());
        vaccineDrive.setName(request.getName());
        vaccineDrive.setApplicableClasses(request.getApplicableClasses());
        vaccineDrive.setScheduledDate(request.getScheduledDate());
        vaccineDrive.setAvailableDozes(request.getAvailableDozes());
        vaccineDrive.setTitle(request.getTitle());
        return vaccineDrive;
    }
}
