package com.example.bankingsystem.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "transactions")
@Data
@NoArgsConstructor
public class transaction {

    @Id
    private ObjectId id;

    private BigDecimal amount;
    
    private String type;

    private LocalDateTime time;

    private String description;

}
