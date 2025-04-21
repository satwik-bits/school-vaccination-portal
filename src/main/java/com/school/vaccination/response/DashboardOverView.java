package com.school.vaccination.response;

import com.school.vaccination.request.VaccinationDriveRequest;
import lombok.Data;

import java.util.List;

@Data
public class DashboardOverView {

    private long totalStudents;

    private long vaccinatedStudents;

    private double vaccinatedPercentage;

    private List<VaccinationDriveRequest> vaccinationDriveRequestList;

}
