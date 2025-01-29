package com.example.bankingsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/loan")
public class loancontroller {

    @GetMapping("/take")
    public String load(){
        return "loan/loan";
    }
}
