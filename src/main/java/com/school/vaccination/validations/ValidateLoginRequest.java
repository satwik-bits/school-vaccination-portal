package com.school.vaccination.validations;

import com.school.vaccination.request.LoginRequest;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

@Component
public class ValidateLoginRequest {

    public static boolean validateLoginRequest(LoginRequest loginRequest) throws Exception {

        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

        if(!loginRequest.getRole().equals("ADMIN")){
            throw new Exception("Only ADMIN roles are allowed to login");
        }
        if(StringUtils.isBlank(loginRequest.getPassword())){
            throw new Exception("Password cannot be null or blank");
        }
        if(!loginRequest.getPassword().matches(passwordPattern)) {
            throw new Exception("Password does not match regex pattern");
        }
        if(StringUtils.isBlank(loginRequest.getUsername())){
            throw new Exception("Username cannot be null or blank");
        }
        if(!isValidDateFormat(loginRequest.getDob()) || StringUtils.isBlank(loginRequest.getDob())){
            throw new Exception("DOB is in incorrect format");
        }
        else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    .withResolverStyle(ResolverStyle.STRICT);
            LocalDate dob = LocalDate.parse(loginRequest.getDob(), formatter);
            LocalDate today = LocalDate.now();
            if(dob.isAfter(today)){
                throw new Exception("Please enter valid DOB");
            }
        }
        if(StringUtils.isBlank(loginRequest.getMobileNo())){
            throw new Exception("Mobile number is blank");
        }
        else{
            if(loginRequest.getMobileNo().length()!=10){
                throw new Exception("Mobile Number digits should be 10");
            }
        }
        return true;

    }

    private static boolean isValidDateFormat(String dobStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                .withResolverStyle(ResolverStyle.STRICT);
        try {
            LocalDate.parse(dobStr, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
