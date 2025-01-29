package com.example.bankingsystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.example.bankingsystem.security.jwtauthenticationfilter;
import com.example.bankingsystem.service.UserDetailsImp;


@Configuration
@EnableWebSecurity
public class springsecrity {
    
    @Autowired
    private UserDetailsImp userDetailsImp;

    // @Autowired
    // private JwtauthenticationFilters JwtauthenticationFilters;

    @Autowired
    private jwtauthenticationfilter jwtauthenticationfilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(request -> request
            .requestMatchers("/login","/login/**","/auth").permitAll()            
            // .requestMatchers("/userpanel/**").hasAuthority("USER")
            .requestMatchers("/adminpanel/**").hasAuthority("admin")
            .anyRequest().permitAll());
            // .anyRequest().authenticated())
            // .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
            // .addFilterBefore(new jwtauthenticationfilter(), UsernamePasswordAuthenticationFilter.class);
            //  .formLogin().disable(); 
        return http.build();
    }

     @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
            http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsImp).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
