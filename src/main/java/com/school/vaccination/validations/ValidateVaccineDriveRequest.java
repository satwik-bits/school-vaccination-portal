package com.school.vaccination.validations;

import com.school.vaccination.request.VaccinationDriveRequest;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ValidateVaccineDriveRequest {

    public static boolean validateVaccineDriveRequest(VaccinationDriveRequest vaccinationDriveRequest) throws Exception {

        if(StringUtils.isBlank(vaccinationDriveRequest.getIdentifier())){
            throw new Exception("Identifier cannot be null/blank!!");
        }
        if(StringUtils.isBlank(vaccinationDriveRequest.getLocation())){
            throw new Exception("Location cannot be blank/null!!");
        }
        if(StringUtils.isBlank(vaccinationDriveRequest.getName())){
            throw new Exception("Name cannot be null/blank!!");
        }
        if(StringUtils.isBlank(vaccinationDriveRequest.getTitle())){
            throw new Exception("Title cannot be null/blank!!");
        }
        if(vaccinationDriveRequest.getApplicableClasses()==null || vaccinationDriveRequest.getApplicableClasses().isEmpty()){
            throw new Exception("Class List cannot be empty/null!!");
        }
        else{
            List<Integer> applicableClasses = vaccinationDriveRequest.getApplicableClasses();
            if(!applicableClasses.stream().allMatch(c-> c>=1 && c<=12)){
                throw new Exception("Classes/Grades are from 1-12, no other grade possible!!");
            }
        }
        if(vaccinationDriveRequest.getAvailableDozes()<=0){
            throw new Exception("Invalid vaccine dozes entered!!");
        }
        if(vaccinationDriveRequest.getScheduledDate().isBefore(LocalDateTime.now())){
            throw new Exception("Scheduled date cannot be before current date!!");
        }
        return true;
    }
}
