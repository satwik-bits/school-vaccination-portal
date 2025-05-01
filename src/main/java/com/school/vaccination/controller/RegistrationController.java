package com.school.vaccination.controller;

import com.school.vaccination.enums.FileDownloadType;
import com.school.vaccination.response.StudentVaccinationResponse;
import com.school.vaccination.service.RegistrationService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/register")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping("/vaccine")
    public ResponseEntity<Object> registerVaccine(
            @RequestParam("studentIdentifier") String studentIdentifier,
            @RequestParam("driveIdentifier") String driveIdentifier
    ){
        try{
            registrationService.registerVaccination(studentIdentifier, driveIdentifier);
            return new ResponseEntity<>("Successfully registered!!", HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/download", produces = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public void download(
            String fileDownloadType,
            HttpServletResponse response
    ) throws Exception {
        try {
            registrationService.download(FileDownloadType.valueOf(fileDownloadType.toUpperCase()), response);
        } catch (Exception e) {
            throw new Exception("Error in Downloading");
        }
    }

    @GetMapping(value = "/display", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> display(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize
    ){
        try{
            Page<StudentVaccinationResponse> studentVaccinationResponsePage = registrationService.display(pageNo, pageSize);
            return new ResponseEntity<>(studentVaccinationResponsePage.getContent(), HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }
}
