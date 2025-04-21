package com.school.vaccination.service.impl;

import com.school.vaccination.repository.StudentRepository;
import com.school.vaccination.response.DashboardOverView;
import com.school.vaccination.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public DashboardOverView getMetrics() {

        DashboardOverView dashboardOverView = new DashboardOverView();
        long totalStudents = studentRepository.countTotalStudents();
        long vaccinatedStudents = studentRepository.countVaccinatedStudents();
        double vaccinatedPercentage = (double) vaccinatedStudents /totalStudents * 100;
        dashboardOverView.setTotalStudents(totalStudents);
        dashboardOverView.setVaccinatedStudents(vaccinatedStudents);
        dashboardOverView.setVaccinatedPercentage(vaccinatedPercentage);
        return dashboardOverView;
    }
}
