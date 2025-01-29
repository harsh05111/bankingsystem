package com.example.bankingsystem.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import lombok.Data;
import lombok.NoArgsConstructor;


@Document(collection = "user_details")
@Data
@NoArgsConstructor

public class user {

    @Id
    private ObjectId id;
    @NonNull
    private String fullname;
    @NonNull
    private String email;
    @NonNull
    private String phonenumber;
    @NonNull
    private String password;

    private BigDecimal bankbalance = BigDecimal.ZERO ;

  

    //@DBRef
    private List<transaction> transactionid = new ArrayList<>();
    private List<String> roles;

    
}
