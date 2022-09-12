package com.grupo04.creditrest.models;

import java.time.LocalDate;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

@Data
public class Payment {
    @Id
    private String id;
    private String targetAccount;
    private float amount;
    @CreatedDate
    private LocalDate date;
    private String dni;
}
