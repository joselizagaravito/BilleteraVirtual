package com.grupo08.card.models;

import lombok.Data;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Data
public class FixedTermSavingsAccount{
    @Id
    private Long id;
    private String type;
    private String customerId;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private float  availableBalance;
}
