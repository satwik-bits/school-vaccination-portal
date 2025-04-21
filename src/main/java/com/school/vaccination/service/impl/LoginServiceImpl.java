package com.school.vaccination.service.impl;

import com.school.vaccination.entity.Login;
import com.school.vaccination.mapper.LoginRequestToLogin;
import com.school.vaccination.repository.LoginRepository;
import com.school.vaccination.request.LoginRequest;
import com.school.vaccination.service.LoginService;
import com.school.vaccination.validations.ValidateLoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginRepository loginRepository;

    @Override
    public String findUserDetailsByUserName(String userName) {
        return loginRepository.findByUsername(userName).getUsername();
    }

    @Override
    public void addAdminUser(LoginRequest loginRequest) throws Exception {
        Login login = LoginRequestToLogin.toEntity(loginRequest);
        if(ValidateLoginRequest.validateLoginRequest(loginRequest)) {

            Login loginAlreadyExists = loginRepository.findByUsername(loginRequest.getUsername());
            if(loginAlreadyExists!=null){
                throw new Exception("Login Details already exists!!");
            }
            else {
                System.out.println("Adding admin user role: " + loginRequest.getUsername());
                loginRepository.save(login);
            }
        }
        else{
            System.out.println("User cannot be added for userName: " + loginRequest.getUsername());
        }
    }
}
