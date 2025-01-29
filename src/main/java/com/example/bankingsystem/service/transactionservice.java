package com.example.bankingsystem.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bankingsystem.entity.transaction;
import com.example.bankingsystem.entity.user;
import com.example.bankingsystem.repository.transactionrepository;

@Service
public class transactionservice {


     @Autowired 
     private userservice userservice;


    @Autowired
    private transactionrepository transactionrepository;

    //save transaction
    public void saveentry(transaction transaction){
        transactionrepository.save(transaction);
    }
    //save transaction into the particular user
    public void saveentry(transaction transaction,String fullname, ObjectId id){
            Optional<user> user = userservice.findbyid(id);
            if (user.isPresent()) {
                user a =user.get();
                transaction save = transactionrepository.save(transaction);
                a.getTransactionid().add(save);
                userservice.saveentry(a);
            }
        // transaction save = transactionrepository.save(transaction);
        // user.getTransactionid().add(save);
        // userservice.saveentry(user);
        
    }

    public transaction transactiones(ObjectId myid,BigDecimal amount,String type,String remark){
        transaction transaction = new transaction();

        transaction.getId();
        transaction.setAmount(amount);
        transaction.setType(type);

        transaction.setDescription(remark);
        transaction.setTime(LocalDateTime.now());

         transaction a = transactionrepository.save(transaction);
         Optional<user> user = userservice.findbyid(myid);
         if (user.isPresent()) {
            user zz = user.get();
            zz.getTransactionid().add(a);
            userservice.savetransaction(zz);
            // user.get().getTransactionid().add(a);
            // userservice.savetransaction(user.get());
         }
         return a;
        
    }
     //get transaction history
    public List<transaction> gettransactionhistory(LocalDateTime start,LocalDateTime end){
        return transactionrepository.findByTimeBetween(start, end);
    }
    //getall transaction
    public List<transaction> gettransaction(){
        return transactionrepository.findAll();    
    }
}
