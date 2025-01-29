package com.example.bankingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bankingsystem.repository.userrepository;

@Service
public class adminservice {
    @Autowired
    private userrepository userrepository;

    @Autowired
    private userservice userservice;

    
}
