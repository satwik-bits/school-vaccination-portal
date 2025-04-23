package com.school.vaccination.service.impl;

import com.school.vaccination.entity.VaccineDrive;
import com.school.vaccination.repository.StudentRepository;
import com.school.vaccination.repository.VaccineDriveRepository;
import com.school.vaccination.response.DashboardOverView;
import com.school.vaccination.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private VaccineDriveRepository vaccineDriveRepository;

    @Override
    public DashboardOverView getMetrics() {

        DashboardOverView dashboardOverView = new DashboardOverView();
        long totalStudents = studentRepository.countTotalStudents();
        long vaccinatedStudents = studentRepository.countVaccinatedStudents();
        double vaccinatedPercentage = (double) vaccinatedStudents /totalStudents * 100;
        List<VaccineDrive> vaccineDriveList = vaccineDriveRepository.findAll();
        List<VaccineDrive> upcomingVaccineDrives = new ArrayList<>();
        for(VaccineDrive vaccineDrive: vaccineDriveList){
            if(LocalDateTime.now().isBefore(vaccineDrive.getScheduledDate())){
                upcomingVaccineDrives.add(vaccineDrive);
            }
        }
        dashboardOverView.setTotalStudents(totalStudents);
        dashboardOverView.setVaccinatedStudents(vaccinatedStudents);
        dashboardOverView.setVaccinatedPercentage(vaccinatedPercentage);
        dashboardOverView.setVaccinationDriveList(upcomingVaccineDrives);
        return dashboardOverView;
    }
}
