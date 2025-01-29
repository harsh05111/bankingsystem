package com.example.bankingsystem.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.bankingsystem.entity.user;




public interface userrepository extends MongoRepository<user,ObjectId> {
    Optional<user> findById(ObjectId id);
    user  findByFullname(String fullname);
    List<user> findAll();
    user findByEmail(String email);
    void deleteByFullname(String fullname);
    long count();
    user save(Optional<user> user);
   // List<user> findByTimeBetween(LocalDateTime start,LocalDateTime end);
   
}
