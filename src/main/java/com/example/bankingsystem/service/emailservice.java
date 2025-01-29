package com.example.bankingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class emailservice {
   
    @Autowired
    private JavaMailSender javaMailSender;

    public boolean sendmail(String to,String subject,String text){
        
        SimpleMailMessage a = new SimpleMailMessage();
        a.setTo(to);
        a.setSubject(subject);
        a.setText(text);
        javaMailSender.send(a);
        return true;
    }




}
