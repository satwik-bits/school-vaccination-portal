package com.school.vaccination.service.impl;

import com.school.vaccination.entity.VaccineDrive;
import com.school.vaccination.mapper.VaccineDriveRequestToVaccine;
import com.school.vaccination.repository.VaccineDriveRepository;
import com.school.vaccination.request.VaccinationDriveRequest;
import com.school.vaccination.service.VaccineService;
import com.school.vaccination.validations.ValidateVaccineDriveRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VaccineServiceImpl implements VaccineService {


    @Autowired
    private VaccineDriveRepository vaccineDriveRepository;

    @Override
    public void addVaccineDriveDetails(VaccinationDriveRequest vaccinationDriveRequest) throws Exception {
        if(ValidateVaccineDriveRequest.validateVaccineDriveRequest(vaccinationDriveRequest)){
            VaccineDrive vaccineDrive = VaccineDriveRequestToVaccine.toEntity(vaccinationDriveRequest);
            try {
                VaccineDrive vaccineDriveExists = vaccineDriveRepository.findByDriveIdentifier(vaccinationDriveRequest.getIdentifier());
                if(vaccineDriveExists!=null){
                    throw new Exception("Student with this ID already exists!!");
                }
                else {
                    LocalDateTime scheduledDate = vaccineDrive.getScheduledDate();
                    LocalDateTime expectedDate = LocalDateTime.now().plusDays(15);

                    if (!scheduledDate.toLocalDate().isEqual(expectedDate.toLocalDate())) {
                        throw new IllegalArgumentException("Scheduled date must be exactly 15 days from today.");
                    }

                    vaccineDriveRepository.save(vaccineDrive);
                }
            }
            catch(Exception e){
                System.out.println("Exception occurred while storing");
            }
        }
        else{
            System.out.println("Invalid Student Request");
        }
    }

    @Override
    public Page<VaccineDrive> fetchVaccineDriveDetails(int pageNo, int pageSize) {
        Pageable pageableStudentInfo = PageRequest.of(pageNo, pageSize);
        return vaccineDriveRepository.findAll(pageableStudentInfo);
    }

    @Override
    public void deleteVaccineDriveDetails(String identifier) throws Exception {
        VaccineDrive vaccineDriveAlreadyExists = vaccineDriveRepository.findByDriveIdentifier(identifier);
        if(vaccineDriveAlreadyExists!=null){
            throw new Exception("Delete not possible as VaccineDrive with this Identifier does not exist!!");
        }
        else{
            vaccineDriveRepository.deleteByDriveIdentifier(identifier);
        }
    }

    @Override
    public VaccineDrive updateVaccinationDriveInfo(VaccinationDriveRequest updatedVaccinationDriveRequest) throws Exception {

        if(ValidateVaccineDriveRequest.validateVaccineDriveRequest(updatedVaccinationDriveRequest)) {

            VaccineDrive vaccineDriveAlreadyExists = vaccineDriveRepository.findByDriveIdentifier(updatedVaccinationDriveRequest.getIdentifier());
            if (vaccineDriveAlreadyExists != null) {
                throw new Exception("Update cannot be possible as student with Identifier is not found!!");
            } else {
                if (!updatedVaccinationDriveRequest.getIdentifier().equals(vaccineDriveAlreadyExists.getDriveIdentifier())) {
                    throw new Exception("Identifier cannot be updated!!");
                }
                if(updatedVaccinationDriveRequest.getScheduledDate().isBefore(LocalDateTime.now())){
                    throw new Exception("Update cannot be possible here as the schedule has passed!!");
                }

                VaccineDrive updatedVaccineDrive = VaccineDriveRequestToVaccine.toEntity(updatedVaccinationDriveRequest);
                vaccineDriveRepository.save(updatedVaccineDrive);
                return updatedVaccineDrive;
            }
        }
        return null;
    }
}
