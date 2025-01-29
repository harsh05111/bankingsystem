package com.example.bankingsystem.controller;

import java.math.BigDecimal;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.bankingsystem.entity.transaction;
import com.example.bankingsystem.entity.user;
import com.example.bankingsystem.service.excelservice;
import com.example.bankingsystem.service.transactionservice;
// import com.example.bankingsystem.entity.user;
import com.example.bankingsystem.service.userservice;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/userpanel")
public class userpanelcontroller {

    @Autowired
    private userservice userservice;

    @Autowired
    private transactionservice transactionservice;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private excelservice excelservice;

    @GetMapping("/{myid}")
    public String userpanelbyuseri(@PathVariable ObjectId myid,Model model){
        // if (myid.length() != 24 || !myid.matches("[0-9a-fA-F]{24}")) {
        //     model.addAttribute("error", "Invalid user ID format");
        //     return "home";
        // }
    
        // Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
        // String a = authentication.getName();
        // ObjectId id = new ObjectId(a);
        Optional<user> user = userservice.findbyid(myid);
        if (user.isPresent()) {    
            model.addAttribute("user", user.get());
            return "userpanel/userhome";
        } else {
            model.addAttribute("error", "User not found");
            return "home";
        }
    }

     //deposit
     @GetMapping("/{myid}/deposit")
     public String getdeposit(@PathVariable ObjectId myid,Model model){
        Optional<user> user = userservice.findbyid(myid);
        model.addAttribute("user", user);
         return "userpanel/deposit";    
     }
     @PostMapping("/{myid}/deposit")
     public String deposit(@PathVariable ObjectId myid,@RequestParam BigDecimal amount,@RequestParam String password,@RequestParam String remark,Model model) {
       Optional<user> user = userservice.findbyid(myid);
       model.addAttribute("user", user);
        if (passwordEncoder.matches(password,  user.get().getPassword())) {
            userservice.adddeposit(amount, myid);
            transaction transaction = transactionservice.transactiones(myid,amount,"DEPOSIT  ",remark);
            model.addAttribute("transaction", transaction);
            return "redirect:/userpanel/"+ user.get().getId();
        }else{
            model.addAttribute("error", "Invalid password");
        }
        return "userpanel/deposit";
       
     }

     //debit                        
    @GetMapping("/{myid}/debit")
    public String getdebit(@PathVariable ObjectId myid,Model model){
        Optional<user> user = userservice.findbyid(myid);
        model.addAttribute("user", user);
        return "userpanel/debit";
    }   
    

    @PostMapping("/{myid}/debit")
    public String debit(@RequestParam BigDecimal amount,@RequestParam String password ,@RequestParam String remark ,Model model,@PathVariable ObjectId myid) {
        Optional<user> user = userservice.findbyid(myid);
        model.addAttribute("user", user);
        if (!user.isPresent()) {
            return "userpanel"+ user.get();
        }

        String validationMessage = userservice.debit(amount  ,myid);
        if (!validationMessage.equals( "amount debited")) {
            model.addAttribute("error", validationMessage);
            System.out.println("invalid amount");
            return "userpanel/debit";
        }
        
            if (passwordEncoder.matches(password, user.get().getPassword())) {
                transaction transaction = transactionservice.transactiones(myid,amount,"DEBIT  ",remark);
                model.addAttribute("transaction", transaction);
              
                return "redirect:/userpanel/" + user.get().getId();
            }
             else{
             model.addAttribute("error", "Invalid Password");
                 return "userpanel/debit";
                }
    }

     //transactiom  
     @GetMapping("/{myid}/transaction")
     public String gettransaction(@PathVariable ObjectId myid,Model model){
        Optional<user> user = userservice.findbyid(myid);
       // model.addAttribute("user", user);
        List<transaction> transactions = user.get().getTransactionid();
         model.addAttribute("user", user);
         model.addAttribute("transactions", transactions);
         return "userpanel/transaction";    
     }

    @GetMapping("/{myid}/change")
    public String change(@PathVariable ObjectId myid,Model model){
        Optional<user> user = userservice.findbyid(myid);
        model.addAttribute("user", user);
        return "userpanel/change";    
    }
    @PostMapping("/{myid}/change")
    public String changes(@RequestParam String email ,@RequestParam String phonenumber,@RequestParam String fullname,@PathVariable ObjectId myid,Model model) {
        Optional<user> user = userservice.findbyid(myid);
        userservice.update(email, phonenumber, fullname,myid);
       return "redirect:/userpanel/"+ user.get().getId();
    }

    @PostMapping("/{myid}/delete")
    public String delete(@PathVariable ObjectId myid,Model model) {
        userservice.dedeleteByfullname(myid);
       return "redirect:/home";
    }


    //loan
    @GetMapping("/{fullname}/loan")
    public String load(){
        return "loan/loan";
    }


    @GetMapping("/{userid}/download-data")
    public ResponseEntity<?> downloadusertransation(@PathVariable ObjectId userid,Model model) throws Exception{
    //    Optional<user> user = userservice.findbyid(id);
        System.out.println("data  download");
        Optional<user> user = userservice.findbyid(userid);
        List<transaction> transaction = user.get().getTransactionid();
        byte[] excelfile  = excelservice.usertrasactionexcel().toByteArray();
        return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename = user_Transaction_history.xlsx")
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .body(excelfile);

        // return "userpanel/transaction";
    }
} 

