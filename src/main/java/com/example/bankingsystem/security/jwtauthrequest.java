package com.example.bankingsystem.security;

import lombok.Data;

@Data
public class jwtauthrequest {
    private String email;
    private String password;
}
