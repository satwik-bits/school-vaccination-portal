package com.school.vaccination.validations;

import com.school.vaccination.request.LoginRequest;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Date;
import java.util.Locale;

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
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
            Date date = sdf.parse(loginRequest.getDob());
            Instant instant = date.toInstant();
            LocalDate dobDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate today = LocalDate.now();
            if(dobDate.isAfter(today)){
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

    public static boolean isValidDateFormat(String dobStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        try {
            Date date = sdf.parse(dobStr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
