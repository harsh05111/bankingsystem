package com.example.bankingsystem.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import com.example.bankingsystem.entity.user;
import com.example.bankingsystem.service.emailservice;
import com.example.bankingsystem.service.userservice;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/")
public class usercontroller {

    @Autowired
    private userservice userservice;
    
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private emailservice emailservice;

    private user currentuser;

    Random a = new Random(1000);

    @GetMapping("home")
    public String home(){
        return "home";
    }

    //registration form
    @GetMapping("/createaccount")
    public String getcreateaccount(Model model) {
        model.addAttribute("user",new user());
        return "createaccount";
    }
    @PostMapping("/create")
    public String createaccount(@ModelAttribute user user,@RequestParam String email,HttpSession session,Model model) {

        int otp = a.nextInt(9999);
        System.out.println("otp is:"+otp);

        String subject = "OTP for verify account";
        String  text = "OTP:" + otp;
        String t0 = email;

        boolean a = this.emailservice.sendmail(t0, subject, text);
        if (a) {
            session.setAttribute("otp", otp);
            session.setAttribute("email",email);
            
        }
        currentuser = user;
        model.addAttribute("user", currentuser);
        return "verifyaccount";
    }
   
   
    @GetMapping("/verifyaccount")
    public String getverifycreateaccount(Model Model) {
        return "verifyaccount";
    }
    @PostMapping("/verifyaccount")
    public String verifyaccount(@RequestParam int otp, user user,@RequestParam String email,HttpSession session){
        Integer myotp = (Integer)session.getAttribute("otp");
        if (myotp == null) {
            System.out.println("otp not found");
            return "not found";
        }
        if (myotp != otp) {
            System.out.println("otp doesnot match");
        }
        if (myotp == otp) {
            System.out.println("otp is correct");
            userservice.saveentry(currentuser);
            userservice.createAdminUser();
            return "redirect:/login";
        }
        
       return "verifyaccount";
    }
    
    //login form
    @GetMapping("/login")
    public String getlogin(Model Model) {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,@RequestParam String password,Model model) {
      
        user user = userservice.getbyemail(email);
        //Optional<user> useri = userservice.findbyid(id);
        if (user == null) {
            return "login"; 
        }
        if ( !passwordEncoder.matches(password, user.getPassword())) {
            return "login";
        }

         if("admin".equals(email)   ) {
            return "redirect:/admin/adminpanel" + user.getId() ;
        }
        passwordEncoder.matches(password, user.getPassword());
            model.addAttribute("user",user);
            System.out.println("email id and password matched ");
           // System.out.println(useri.get().getId());
            return "redirect:/userpanel/" + user.getId();
           
       // return "login";
    }
   
    //userhome
    @GetMapping("/userhome")    
    public String getuserhome(Model model) {
            return "userpanel/userhome";
    }
}
