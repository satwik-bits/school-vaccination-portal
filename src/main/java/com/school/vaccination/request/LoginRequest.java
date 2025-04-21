package com.school.vaccination.request;

import jakarta.persistence.Column;
import lombok.*;


@Data
@Builder
@Getter
@Setter
public class LoginRequest {

    @Column(nullable = false, unique = true)
    private String username;

    private String password;

    private String email;

    private String dob;

    private String role;

    private String mobileNo;

}
