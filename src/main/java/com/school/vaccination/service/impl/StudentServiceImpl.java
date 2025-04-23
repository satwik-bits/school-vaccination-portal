package com.school.vaccination.service.impl;

import com.school.vaccination.entity.Student;
import com.school.vaccination.mapper.StudentRequestToStudent;
import com.school.vaccination.repository.StudentRepository;
import com.school.vaccination.request.StudentRequest;
import com.school.vaccination.service.StudentService;
import com.school.vaccination.validations.ValidateStudentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public void addStudent(StudentRequest studentRequest) throws Exception {

        if(ValidateStudentRequest.validateStudentRequest(studentRequest)){
            Student student = StudentRequestToStudent.toEntity(studentRequest);
            try {
                Student studentExists = studentRepository.findByStudentIdentifier(studentRequest.getIdentifier());
                if(studentExists!=null){
                    throw new Exception("Student with this ID already exists!!");
                }
                else {
                    studentRepository.save(student);
                }
            }
            catch(Exception e){
                System.out.println("Exception occurred while storing");
            }
        }
        else{
            System.out.println("Invalid Student Request");
        }
    }

    @Override
    public Page<Student> fetchStudentDetails(int pageNo, int pageSize) {
        Pageable pageableStudentInfo = PageRequest.of(pageNo, pageSize);
        return studentRepository.findAll(pageableStudentInfo);
    }

    @Override
    public Page<Student> fetchStudentDetailsByIdentifier(String identifier, int pageNo, int pageSize) {
        Pageable pageableStudentInfo = PageRequest.of(pageNo, pageSize);
        return studentRepository.findByAnyIdentifier(identifier, pageableStudentInfo);
    }

    @Override
    public Page<Student> fetchStudentDetailsByName(String name, int pageNo, int pageSize) {
        Pageable pageableStudentInfo = PageRequest.of(pageNo, pageSize);
        return studentRepository.findByName(name, pageableStudentInfo);
    }

    @Override
    public Page<Student> fetchStudentDetailsByClassId(int classId, int pageNo, int pageSize) {
        Pageable pageableStudentInfo = PageRequest.of(pageNo, pageSize);
        return studentRepository.findByClassId(classId, pageableStudentInfo);
    }

    @Override
    public Page<Student> fetchStudentDetailsWhichAreVaccinated(boolean isVaccinated, int pageNo, int pageSize) {
        Pageable pageableStudentInfo = PageRequest.of(pageNo, pageSize);
        return studentRepository.findByIsVaccinated(isVaccinated, pageableStudentInfo);
    }

    @Override
    public void deleteStudent(String identifier) throws Exception {
        Student studentAlreadyExists = studentRepository.findByStudentIdentifier(identifier);
        if(studentAlreadyExists!=null){
            throw new Exception("Delete not possible as Student with this Identifier does not exist!!");
        }
        else{
            studentRepository.deleteByStudentIdentifier(identifier);
        }
    }

    @Override
    public Student updateStudentInfo(StudentRequest updatedStudentRequest) throws Exception {

        if(ValidateStudentRequest.validateStudentRequest(updatedStudentRequest)) {

            Student studentAlreadyExists = studentRepository.findByStudentIdentifier(updatedStudentRequest.getIdentifier());
            if (studentAlreadyExists != null) {
                throw new Exception("Update cannot be possible as student with Identifier is not found!!");
            } else {
                if (!updatedStudentRequest.getIdentifier().equals(studentAlreadyExists.getStudentIdentifier())) {
                    throw new Exception("Identifier cannot be updated!!");
                }
                Student updatedStudent = StudentRequestToStudent.toEntity(updatedStudentRequest);
                studentRepository.save(updatedStudent);
                return updatedStudent;
            }
        }
        return null;
    }

    @Override
    public Map<String, Object> bulkUploadStudentCsv(MultipartFile file) throws Exception {

        if(file.isEmpty()){
            throw new Exception("File is empty!!");
        }
        List<Student> validStudents = new ArrayList<>();
        List<String> errorList = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))){
            String line;
            int lineNumber =0;

            while((line = reader.readLine())!=null){
                lineNumber++;

                String[] columns = line.split(",");

                if (columns.length != 6) {
                    errorList.add("Line " + lineNumber + ": Incorrect number of columns.");
                    continue;
                }

                StudentRequest request = parseRow(columns);
                List<String> validationErrors = validateStudentRequest(request);

                if (validationErrors.isEmpty()) {
                    validStudents.add(mapToStudent(request));
                } else {
                    errorList.add("Line " + lineNumber + ": " + String.join("; ", validationErrors));
                }
            }

            studentRepository.saveAll(validStudents);

            Map<String, Object> result = new HashMap<>();
            result.put("savedCount", validStudents.size());
            result.put("errors", errorList);
            return result;
        }
    }

    private StudentRequest parseRow(String[] row) {
        return StudentRequest.builder()
                .identifier(row[0].trim())
                .name(row[1].trim())
                .age(Integer.parseInt(row[2].trim()))
                .classId(Integer.parseInt(row[3].trim()))
                .mobileNo(row[4].trim())
                .isVaccinated(Boolean.parseBoolean(row[5].trim()))
                .build();
    }

    private List<String> validateStudentRequest(StudentRequest student) {
        List<String> errors = new ArrayList<>();

        if (student.getIdentifier() == null || student.getIdentifier().isEmpty())
            errors.add("Identifier is required.");

        if (student.getName() == null || student.getName().isEmpty())
            errors.add("Name is required.");

        if (student.getAge() <= 0 || student.getAge() > 100)
            errors.add("Age must be between 1 and 100.");

        if (student.getClassId() < 1 || student.getClassId() > 12)
            errors.add("Class ID must be between 1 and 12.");

        if (student.getMobileNo() == null || !student.getMobileNo().matches("\\d{10}"))
            errors.add("Mobile number must be 10 digits.");

        return errors;
    }

    private Student mapToStudent(StudentRequest request) {
        return Student.builder()
                .studentIdentifier(request.getIdentifier())
                .name(request.getName())
                .age(request.getAge())
                .classId(request.getClassId())
                .mobileNo(request.getMobileNo())
                .isVaccinated(request.isVaccinated())
                .build();
    }
}
