package com.school.vaccination.service;

import com.school.vaccination.entity.Student;
import com.school.vaccination.request.StudentRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public interface StudentService {

    void addStudent(StudentRequest studentRequest) throws Exception;

    Page<Student> fetchStudentDetails(int pageNo, int pageSize);

    Page<Student> fetchStudentDetailsByIdentifier(String identifier, int pageNo, int pageSize);

    Page<Student> fetchStudentDetailsByName(String name, int pageNo, int pageSize);

    Page<Student> fetchStudentDetailsByClassId(int classId, int pageNo, int pageSize);

    Page<Student> fetchStudentDetailsWhichAreVaccinated(boolean isVaccinated, int pageNo, int pageSize);

    void deleteStudent(String identifier) throws Exception;

    Student updateStudentInfo(StudentRequest studentRequest) throws Exception;

    Map<String, Object> bulkUploadStudentCsv(MultipartFile file) throws Exception;
}
