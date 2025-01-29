package com.example.bankingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.bankingsystem.entity.user;
import com.example.bankingsystem.repository.userrepository;

@Service
public class UserDetailsImp implements UserDetailsService{

    @Autowired
    private userrepository userrepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      System.out.println("hello ");
      user user = userrepository.findByFullname(username);
      // String[] roles = user.getRoles()  != null  ? user.getRoles().toArray(new String[0]) : new String[0];
      if ("admin".equals(username)) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username("admin")  
                    .password(user.getPassword()) 
                    .roles("admin")  // ROLE_ADMIN
                    .build();
        } 
      return org.springframework.security.core.userdetails.User.builder().username(user.getFullname()).password(user.getPassword()).roles("USER").build();
      // throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
    }
    
    // @Override
    // public UserDetails loadUserByUsername(String fullname) throws UsernameNotFoundException {

    //   System.out.println(fullname+" user mail");
    //    user user = this.userrepository.findByFullname(fullname);
    //    if (user == null) {
    //     throw new UsernameNotFoundException(" user not found");
    //    }
    //    if ("admin".equals(fullname)) {
    //     return org.springframework.security.core.userdetails.User.builder()
    //             .username("admin")  
    //             .password(user.getPassword()) // bcrypt hashed "admin123"
    //             .roles("admin")  // ROLE_ADMIN
    //             .build();
    // } 
    // System.out.println(user.getRoles()+"users role");
    //    String[] roles = user.getRoles() != null ? user.getRoles().toArray(new String[0]) : new String[0];
    //    return org.springframework.security.core.userdetails.User.builder().username(user.getEmail()).password(user.getPassword()).roles(roles).build();
    // }
    

}
