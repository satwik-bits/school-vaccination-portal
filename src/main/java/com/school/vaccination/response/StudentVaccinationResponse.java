package com.school.vaccination.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudentVaccinationResponse {

    private String studentName;

    private String studentMobileNo;

    private boolean isVaccinated;

    private LocalDateTime vaccinationDate;

    private String vaccineName;

}
