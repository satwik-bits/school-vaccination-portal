package com.school.vaccination.service.impl;

import com.school.vaccination.entity.Student;
import com.school.vaccination.entity.StudentVaccination;
import com.school.vaccination.entity.VaccineDrive;
import com.school.vaccination.repository.StudentRepository;
import com.school.vaccination.repository.StudentVaccinationRepository;
import com.school.vaccination.repository.VaccineDriveRepository;
import com.school.vaccination.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private VaccineDriveRepository vaccineDriveRepository;

    @Autowired
    private StudentVaccinationRepository studentVaccinationRepository;


    @Override
    public void registerVaccination(String studentIdentifier, String driveIdentifier) {
        Student student = studentRepository.findByStudentIdentifier(studentIdentifier);
        if(student==null) {
            throw new RuntimeException("Student Details Not Found!!");
        }

        VaccineDrive vaccineDrive = vaccineDriveRepository.findByDriveIdentifier(driveIdentifier);
        if(vaccineDrive==null){
            throw new RuntimeException("Vaccine drive not found!!");
        }

        boolean isAlreadyVaccinated = studentVaccinationRepository.existsByStudentIdAndVaccineName(studentIdentifier, vaccineDrive.getName());
        if(isAlreadyVaccinated){
            throw new RuntimeException("Student is already vaccinated for the same vaccine!!");
        }
        StudentVaccination studentVaccination = new StudentVaccination();
        studentVaccination.setVaccinationDate(LocalDateTime.now());
        studentVaccination.setStudent(student);
        studentVaccination.setDrive(vaccineDrive);
        studentVaccinationRepository.save(studentVaccination);

        student.setVaccinated(true);
        studentRepository.save(student);
    }
}
