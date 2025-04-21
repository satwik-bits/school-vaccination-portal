package com.school.vaccination.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class StudentRequest {

    private String identifier;

    private String name;

    private int age;

    private int classId;

    private String mobileNo;

    private boolean isVaccinated;
}
