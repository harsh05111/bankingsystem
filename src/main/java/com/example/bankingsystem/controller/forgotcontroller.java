package com.example.bankingsystem.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.bankingsystem.entity.user;
import com.example.bankingsystem.service.emailservice;
import com.example.bankingsystem.service.userservice;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class forgotcontroller {
    Random a = new Random(1000);

    @Autowired
    private emailservice emailservice;

    @Autowired
    private userservice userservice;

   @Autowired
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

     @GetMapping("/forgot")
    public String forgot(Model Model) {
        return "forgot/forgot";
    }

    @PostMapping("/forgot")
    public String sendotp(Model Model,@RequestParam String email,HttpSession session) {
       
        int otp = a.nextInt(9999);
        System.out.println("OTP:"+otp);

        String subject = "OTP for Bankingsystem";
        String text = "OTP: " + otp;
        String to = email;

        boolean a = this.emailservice.sendmail(to, subject, text);
        if (a) {
            session.setAttribute("otp", otp); 
            session.setAttribute("email", email); 
            return "redirect:/verify";
        }
        return "forgot/forgot";
       
    }
   
   

    @GetMapping("/verify")  
    public String verify(Model Model) {
        return "forgot/verify_otp";
    }
    @PostMapping("/verify")
    public String veriftotp(@RequestParam int otp,HttpSession session){
        
        Integer myotp = (Integer) session.getAttribute("otp");
        String email = (String) session.getAttribute("email");


        if (myotp == null) {
            System.out.println("otp not found");
            return "not found";
        }
       if (myotp != otp) {  
    
        System.out.println("enter valid otp");
       }

        if (myotp == otp) {
            System.out.println("otp is valid");
            user user = userservice.getbyemail(email);
            if (user == null) {
                System.err.println("user doesnot exists");
            }else{
                System.out.println("otp  is valid");
                return "redirect:/newpassword";
            }
        }
        return "forgot/verify_otp";
       
    }



    @GetMapping("/newpassword")  
    public String newpassword(Model Model) {
        return "forgot/newpassword";
    }

    @PostMapping("/newpassword")
    public String resetpassword(@RequestParam String password,@RequestParam String confirmpassword,HttpSession session){
        if (password.equals(confirmpassword)) {
            String email = (String) session.getAttribute("email");
            user user = userservice.getbyemail(email);
       
                user.setPassword(passwordEncoder.encode(password));
                userservice.saveq(user);
                return "redirect:/login";
    
        }
       
        return "forgot/newpassword";
    }

    
}
