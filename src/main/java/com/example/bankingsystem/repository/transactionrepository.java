package com.example.bankingsystem.repository;

import java.time.LocalDateTime;
import java.util.List;  

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.bankingsystem.entity.transaction;

public interface transactionrepository extends MongoRepository<transaction,ObjectId>{
    List<transaction> findByTimeBetween(LocalDateTime start,LocalDateTime end);

    
}
