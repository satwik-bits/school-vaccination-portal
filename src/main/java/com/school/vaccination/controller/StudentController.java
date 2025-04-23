package com.school.vaccination.controller;


import com.school.vaccination.entity.Student;
import com.school.vaccination.request.StudentRequest;
import com.school.vaccination.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;


@RestController
@RequestMapping("/v1/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/add")
    public ResponseEntity<Object> addStudent(
            @Validated
            @RequestBody StudentRequest studentRequest) {
        try {
            studentService.addStudent(studentRequest);
            return ResponseEntity.ok().body("Successfully added student");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping("/fetchAll")
    public ResponseEntity<Object> getStudentDetails(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        try {
            Page<Student> students = studentService.fetchStudentDetails(pageNo, pageSize);
            return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping("/fetch/{identifier}")
    public ResponseEntity<Object> getStudentByIdentifier(
            @PathVariable String identifier,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        try {
            Page<Student> students = studentService.fetchStudentDetailsByIdentifier(identifier, pageNo, pageSize);
            return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping("/fetch/{name}")
    public ResponseEntity<Object> getStudentByName(
            @PathVariable String name,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        try {
            Page<Student> students = studentService.fetchStudentDetailsByName(name, pageNo, pageSize);
            return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping("/fetch/{classId}")
    public ResponseEntity<Object> getStudentByName(
            @PathVariable int classId,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        try {
            Page<Student> students = studentService.fetchStudentDetailsByClassId(classId, pageNo, pageSize);
            return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping("/fetch/{isVaccinated}")
    public ResponseEntity<Object> getStudentByName(
            @PathVariable boolean isVaccinated,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        try {
            Page<Student> students = studentService.fetchStudentDetailsWhichAreVaccinated(isVaccinated, pageNo, pageSize);
            return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @DeleteMapping("/delete/{identifier}")
    public ResponseEntity<Object> deleteStudentDetails(
            @PathVariable String identifier
    ) {
        try {
            studentService.deleteStudent(identifier);
            return ResponseEntity.ok().body("Successfully deleted Student");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PutMapping("/update/{identifier}")
    public ResponseEntity<Object> updateStudentRecord(
            @Validated
            @RequestBody StudentRequest studentRequest, @PathVariable String identifier) {
        try {
            Student updatedStudent = studentService.updateStudentInfo(studentRequest);
            return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping(value = "/uploadCsv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> bulkUpload(
            @RequestParam("file") MultipartFile file
    ) {
        try {
            Map<String, Object> response = studentService.bulkUploadStudentCsv(file);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }
}
