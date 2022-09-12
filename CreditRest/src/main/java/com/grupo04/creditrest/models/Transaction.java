package com.grupo04.creditrest.models;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Transaction {
    private String id;
    private String type;
    private String sourceAccount;
    private String targetAccount;
    private float amount;
    private LocalDate date;
    private String dni;
}
