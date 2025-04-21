package com.school.vaccination.controller;

import com.school.vaccination.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/register")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping("/vaccine")
    public ResponseEntity<Object> registerVaccine(
            @PathVariable("studentIdentifier") String studentIdentifier,
            @PathVariable("driveIdentifier") String driveIdentifier
    ){
        try{
            registrationService.registerVaccination(studentIdentifier, driveIdentifier);
            return new ResponseEntity<>("Successfully registered!!", HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }
}
