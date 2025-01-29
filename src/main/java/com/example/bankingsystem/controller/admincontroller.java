package com.example.bankingsystem.controller;

import java.io.IOException;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.bankingsystem.entity.user;
import com.example.bankingsystem.service.excelservice;
import com.example.bankingsystem.service.userservice;


@Controller
@RequestMapping("admin")
public class admincontroller {

    @Autowired
    private userservice userservice;

    @Autowired
    private excelservice excelservice;

    @GetMapping("/{myid}")
    public String adminpanel(Model model){
        long usercount = userservice.countuser();
        model.addAttribute("usercount",usercount);
      //  long activecount = userservice.countactiveuser();
       
      //  model.addAttribute("activecount",activecount);
        return "admin/adminpanel";
    }
    @GetMapping("/users")
    public String users(Model model){
        List<user> users = userservice.getalluser();
        model.addAttribute("users",users);
        return "admin/users";
    }

    @GetMapping("/newuser")
    public String newusers(Model model){

        return "createaccount";
    }
    @PostMapping("/{myid}/delete")
    public String deleteEntity(@PathVariable ObjectId myid) {
       userservice.dedeleteByfullname(myid);
       
        return "redirect:/admin/users";  
    }

    // @GetMapping("/userdata")
    // public ResponseEntity<> downloadexcelfile()
        
    //     System.out.println("file genrated");
    //     return "redirect:/admin/users";
    // }

    @GetMapping("/userdata")
    public ResponseEntity<byte[]> downloadExcel() throws Exception {
        // Generate the Excel file
        byte[] excelFile = excelservice.generateexcel().toByteArray();

        // Set the content type and header for the file download
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=employees.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(excelFile);
    }
    
    // @PostMapping("/{myid}/update")
    // public String updateuser(@PathVariable ObjectId myid,@RequestParam String fullname,@RequestParam String email ,@RequestParam String phonenumber) {
    //    // System.out.println("Received email: " + email);
    //     userservice.update(email, phonenumber,fullname,myid);
   
    //     System.out.println("user updated "+fullname);
    //     return "redirect:/admin/users";  // After deletion, redirect to the list page
    // }

}
// 