package com.example.bankingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.bankingsystem.security.JwtHelper;
import com.example.bankingsystem.security.jwtauthrequest;
import com.example.bankingsystem.security.jwtauthresponse;
import com.example.bankingsystem.service.UserDetailsImp;

@Controller
@RequestMapping("/auth")
public class authcontroller {

    

    @Autowired
    private UserDetailsImp userdetailsmplservice;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;
    

    @PutMapping("/login")
    public ResponseEntity<jwtauthresponse> createtoken (@RequestBody jwtauthrequest request){
        this.auth(request.getEmail(),request.getPassword());
        UserDetails userDetails = this.userdetailsmplservice.loadUserByUsername(request.getEmail());
        String token = this.jwtHelper.generateToken(userDetails);

        jwtauthresponse a = new jwtauthresponse();
        a.setToken(token);
                return new ResponseEntity<>(a,HttpStatus.OK);
     }
     private void auth(String email, String password) {
       UsernamePasswordAuthenticationToken a = new UsernamePasswordAuthenticationToken(email,password);
       this.authenticationManager.authenticate(a);
       
    }
        
           
}
