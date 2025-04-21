package com.school.vaccination.validations;

import com.school.vaccination.request.StudentRequest;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class ValidateStudentRequest {

    public static boolean validateStudentRequest(StudentRequest studentRequest) throws Exception {

        if(StringUtils.isBlank(studentRequest.getIdentifier())){
            throw new Exception("Request ID of student cannot be null/blank");
        }

        if(StringUtils.isBlank(String.valueOf(studentRequest.getAge()))){
            throw new Exception("Age cannot be null/blank");
        }

        if(StringUtils.isBlank(studentRequest.getMobileNo())){
            throw new Exception("Mobile number cannot be null/blank");
        }
        else{
            if(studentRequest.getMobileNo().length()!=10){
                throw new Exception("Mobile Number entered is invalid");
            }
        }
        if(StringUtils.isBlank(studentRequest.getName())){
            throw new Exception("Name cannot be null/blank");
        }
        if(StringUtils.isBlank(studentRequest.getIdentifier())){
            throw new Exception("Identifier cannot be null/blank");
        }
        return true;
    }
}
