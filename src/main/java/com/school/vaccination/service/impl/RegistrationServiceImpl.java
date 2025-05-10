package com.school.vaccination.service.impl;

import com.school.vaccination.entity.Student;
import com.school.vaccination.entity.StudentVaccination;
import com.school.vaccination.entity.VaccineDrive;
import com.school.vaccination.enums.FileDownloadType;
import com.school.vaccination.repository.StudentRepository;
import com.school.vaccination.repository.StudentVaccinationRepository;
import com.school.vaccination.repository.VaccineDriveRepository;
import com.school.vaccination.response.StudentVaccinationResponse;
import com.school.vaccination.service.FileDownloadService;
import com.school.vaccination.service.RegistrationService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private VaccineDriveRepository vaccineDriveRepository;

    @Autowired
    private StudentVaccinationRepository studentVaccinationRepository;

    @Autowired
    private FileDownloadService pdfFileDownloadService;

    @Autowired
    private FileDownloadService excelFileDownloadService;

    @Autowired
    private FileDownloadService csvFileDownloadService;

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

        Long isAlreadyVaccinated = studentVaccinationRepository.existsByStudentIdAndVaccineName(studentIdentifier, vaccineDrive.getName());
        if(isAlreadyVaccinated==1){
            throw new RuntimeException("Student is already vaccinated for the same vaccine!!");
        }

        int studentClassId = student.getClassId();
        List<Integer> applicableClassesForDrive = vaccineDrive.getApplicableClasses();
        if(!applicableClassesForDrive.contains(studentClassId)){
            throw new RuntimeException("Class of the student is not eligible for this vaccine!!");
        }

        StudentVaccination studentVaccination = new StudentVaccination();
        studentVaccination.setVaccinationDate(LocalDateTime.now());
        studentVaccination.setStudent(student);
        studentVaccination.setDrive(vaccineDrive);
        studentVaccinationRepository.save(studentVaccination);
        student.setVaccinated(true);
        vaccineDrive.setAvailableDozes(vaccineDrive.getAvailableDozes()-1);
        vaccineDriveRepository.save(vaccineDrive);
        studentRepository.save(student);
    }

    @Override
    public void download(FileDownloadType fileDownloadType, HttpServletResponse response) throws Exception {
        List<StudentVaccinationResponse> studentVaccinationResponseList = new ArrayList<>();
        List<StudentVaccination> studentVaccinationList = studentVaccinationRepository.findAll();
        for(StudentVaccination studentVaccination: studentVaccinationList){
            StudentVaccinationResponse studentVaccinationResponse = getStudentVaccinationResponse(studentVaccination);
            studentVaccinationResponseList.add(studentVaccinationResponse);
        }
        switch(fileDownloadType) {
            case FileDownloadType.CSV:
                csvFileDownloadService.downloadFile(studentVaccinationResponseList, response);
                break;

            case FileDownloadType.EXCEL:
                excelFileDownloadService.downloadFile(studentVaccinationResponseList, response);
                break;

            case FileDownloadType.PDF:
                pdfFileDownloadService.downloadFile(studentVaccinationResponseList, response);
                break;

            default:
                throw new Exception("No other type possible");
        }


    }

    @Override
    public Page<StudentVaccinationResponse> display(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<StudentVaccination> studentVaccinationPage = studentVaccinationRepository.findAll(pageable);
        return studentVaccinationPage.map(this::getStudentVaccinationResponse);
    }

    private StudentVaccinationResponse getStudentVaccinationResponse(StudentVaccination studentVaccination) {
        StudentVaccinationResponse studentVaccinationResponse = new StudentVaccinationResponse();
        studentVaccinationResponse.setVaccineName(studentVaccination.getDrive().getName());
        studentVaccinationResponse.setStudentName(studentVaccination.getStudent().getName());
        studentVaccinationResponse.setVaccinationDate(studentVaccination.getVaccinationDate());
        studentVaccinationResponse.setVaccinated(studentVaccination.getStudent().isVaccinated());
        studentVaccinationResponse.setStudentMobileNo(studentVaccination.getStudent().getMobileNo());
        return studentVaccinationResponse;
    }

}
