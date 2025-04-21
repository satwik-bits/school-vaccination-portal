package com.school.vaccination.controller;

import com.school.vaccination.request.LoginRequest;
import com.school.vaccination.service.LoginService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(
            @RequestBody LoginRequest loginRequest){
        String userName = loginRequest.getUsername();
        String password = loginService.findUserDetailsByUserName(userName);
        if(StringUtils.isNotBlank(password) && password.equals(loginRequest.getPassword())){
            String token = UUID.randomUUID().toString();

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("message", "Login successful");

            return ResponseEntity.ok(response);
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid credentials"));
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Object> signup(
            @RequestBody LoginRequest loginRequest) throws Exception {
        loginService.addAdminUser(loginRequest);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User added successfully");
        return ResponseEntity.ok(response);
    }
}
