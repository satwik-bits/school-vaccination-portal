package com.school.vaccination.request;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class VaccinationDriveRequest {

    private String identifier;

    private String name;

    private int availableDozes;

    private List<Integer> applicableClasses;

    private String title;

    private LocalDateTime scheduledDate;

    private String location;
}
