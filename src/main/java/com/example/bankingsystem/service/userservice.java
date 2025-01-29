package com.example.bankingsystem.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.bankingsystem.entity.user;
import com.example.bankingsystem.repository.userrepository;

@Service
public class userservice {

    @Autowired
    private userrepository userrepository;

    @Autowired
    private  final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    //save user
    public void saveentry(user user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        userrepository.save(user);
    }
    public void savetransaction(user user){
            userrepository.save(user); 
        
    
    }

    public void createAdminUser() {
        // Check if the admin user already exists
        if (userrepository.findByFullname("admin") == null) {
            // Create and save the admin user
            user admin = new user();
            admin.setFullname("admin");
            admin.setEmail("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRoles(Arrays.asList("admin"));
            admin.setBankbalance(null);
            userrepository.save(admin); // Save to the database
            System.out.println("Admin user created successfully.");
        }
    }

    public List<user> getalluser(){
        return userrepository.findAll();
    }

    public void saveq(user a){
        userrepository.save(a);
    }

    //find by fulllname
    public user getbyfullname(String fullname){
        return userrepository.findByFullname(fullname);
    }

    //find by email
    public user getbyemail(String email){
        return userrepository.findByEmail(email);
    }
    //find by id
    public Optional<user> findbyid(ObjectId id){
        return userrepository.findById(id);
    }

    public void dedeleteByfullname(ObjectId myid){
        Optional<user> user = userrepository.findById(myid);
       if (user != null) {
        userrepository.deleteById(myid);
       }
    }
    //deposit
    public user adddeposit(BigDecimal amount,ObjectId myid){
        Optional<user> user = userrepository.findById(myid);
        if (user != null) {
            user.get().setBankbalance(user.get().getBankbalance().add(amount));
            return userrepository.save(user.get());
        }
        else{
            System.out.println("User not found with full name: " + myid);
        throw new RuntimeException("User not found: " + myid);
         
        }
        
    
    }
    //debit
    public String debit(BigDecimal amount,ObjectId myid){
        Optional<user> user = userrepository.findById(myid);

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return "valid";
        }
        user a = user.get();
        if (amount.compareTo(a.getBankbalance()) > 0) {
            System.out.println("invalid amount");
            return "Invalid amount";
        }
            a.setBankbalance(a.getBankbalance().subtract(amount));
            userrepository.save(a);
            System.out.println("debited");
            return "amount debited";
        
    
    }
    
    //update
    public void update(String email,String phonenumber,String fullname,ObjectId myid){
        Optional<user> user = userrepository.findById(myid);
        if (user.isPresent()) {
            user a = user.get();
            a.setFullname(fullname);
            a.setEmail(email);
            a.setPhonenumber(phonenumber);
            user.get().setFullname(fullname);
            user.get().setEmail(email);
            user.get().setPhonenumber(phonenumber);
           // user.setPassword(password);
            userrepository.save(a);
        }else{
            throw new RuntimeException("user does not found" + myid);
        }
      
    }

    //count user
    public long countuser(){
        return userrepository.count();
    }
    

    

  
   
}
