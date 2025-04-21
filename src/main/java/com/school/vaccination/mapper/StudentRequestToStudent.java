package com.school.vaccination.mapper;

import com.school.vaccination.entity.Student;
import com.school.vaccination.request.StudentRequest;

public class StudentRequestToStudent {

    public static Student toEntity(StudentRequest request) {
        Student student = new Student();
        student.setStudentIdentifier(request.getIdentifier());
        student.setName(request.getName());
        student.setAge(request.getAge());
        student.setClassId(request.getClassId());
        student.setMobileNo(request.getMobileNo());
        student.setVaccinated(request.isVaccinated());
        return student;
    }
}
