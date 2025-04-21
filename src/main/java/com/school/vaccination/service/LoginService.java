package com.school.vaccination.service;

import com.school.vaccination.request.LoginRequest;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {

    String findUserDetailsByUserName (String userName);

    void addAdminUser (LoginRequest loginRequest) throws Exception;

}

