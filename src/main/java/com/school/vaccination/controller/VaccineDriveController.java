package com.school.vaccination.controller;

import com.school.vaccination.entity.VaccineDrive;
import com.school.vaccination.request.VaccinationDriveRequest;
import com.school.vaccination.service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/vaccine-drive")
public class VaccineDriveController {

    @Autowired
    private VaccineService vaccineService;

    @PostMapping("/addVaccineDrive")
    public ResponseEntity<Object> addVaccineDriveDetails(
            @Validated
            @RequestBody VaccinationDriveRequest vaccinationDriveRequest){
        try {
            vaccineService.addVaccineDriveDetails(vaccinationDriveRequest);
            return ResponseEntity.ok().body("Successfully added student");
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping("/fetchAllDrives")
    public ResponseEntity<Object> getVaccineDetails(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize
    ){
        try{
            Page<VaccineDrive> students = vaccineService.fetchVaccineDriveDetails(pageNo, pageSize);
            return new ResponseEntity<>(students, HttpStatus.OK);
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }

    @DeleteMapping("/delete/{identifier}")
    public ResponseEntity<Object> deleteVaccineDriveDetails(
            @PathVariable String identifier
    ){
        try{
            vaccineService.deleteVaccineDriveDetails(identifier);
            return ResponseEntity.ok().body("Successfully deleted Student");
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PutMapping("/update/{identifier}")
    public ResponseEntity<Object> updateVaccineDriveRecord(
            @Validated
            @RequestBody VaccinationDriveRequest vaccinationDriveRequest, @PathVariable String identifier){
        try{
            VaccineDrive updatedVaccinationDriveInfo = vaccineService.updateVaccinationDriveInfo(vaccinationDriveRequest);
            return new ResponseEntity<>(updatedVaccinationDriveInfo, HttpStatus.OK);
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }
}
