package com.school.vaccination.mapper;

import com.school.vaccination.entity.Login;
import com.school.vaccination.request.LoginRequest;

public class LoginRequestToLogin {

    public static Login toEntity(LoginRequest request) {
        Login login = new Login();
        login.setUsername(request.getUsername());
        login.setPassword(request.getPassword());
        login.setEmail(request.getEmail());
        login.setDob(request.getDob());
        login.setRole(request.getRole());
        login.setMobileNo(request.getMobileNo());
        return login;
    }
}
