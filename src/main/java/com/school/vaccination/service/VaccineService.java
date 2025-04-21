package com.school.vaccination.service;


import com.school.vaccination.entity.VaccineDrive;
import com.school.vaccination.request.VaccinationDriveRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface VaccineService {

    void addVaccineDriveDetails(VaccinationDriveRequest vaccinationDriveRequest) throws Exception;

    Page<VaccineDrive> fetchVaccineDriveDetails(int pageNo, int pageSize);

    void deleteVaccineDriveDetails(String identifier) throws Exception;

    VaccineDrive updateVaccinationDriveInfo(VaccinationDriveRequest vaccinationDriveRequest) throws Exception;
}
