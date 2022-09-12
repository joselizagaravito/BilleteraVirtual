package com.grupo04.customer.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Transaction {
	
	@Id
    private String id;
    private String type;
    private String sourceAccount;
    private String targetAccount;
    private float amount;
    @CreatedDate
    private LocalDateTime date;
    private String dni;
}
