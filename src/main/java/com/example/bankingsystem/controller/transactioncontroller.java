package com.example.bankingsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.bankingsystem.entity.transaction;
import com.example.bankingsystem.entity.user;
import com.example.bankingsystem.service.userservice;

@Controller
@RequestMapping("/transaction")
public class transactioncontroller {

    @Autowired
    private userservice userservice;

    
    @GetMapping("/{fullname}/transaction")
     public String gettransaction(@PathVariable String fullname,Model model){
        user user = userservice.getbyfullname(fullname);
        List<transaction> transactions = user.getTransactionid();
         model.addAttribute("user", user);
         model.addAttribute("transactions", transactions);
         return "transaction";    
     }
}
