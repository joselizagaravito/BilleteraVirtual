package com.grupo04.customer.models;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

	private Long id;
    private String type;
    private String customerId;
    private String typeProfileAccount;
    private boolean commissionStatus;
    private double commissionAmount;
    private int limitTransaction;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private float  availableBalance;
	
}
