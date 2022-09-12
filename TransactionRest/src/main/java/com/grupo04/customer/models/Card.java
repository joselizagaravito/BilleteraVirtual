package com.grupo04.customer.models;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Card {
	private String id;
	private String numdocument;
	private String typecard; //d=debito; c=credito
	private String PrincipalAccount;
    private String currentaccount;
    private String fixedtermsavingsaccount;
    private String savingsAccount;
    private LocalDate createdAtDate;
    private LocalDate updatedAt;
}
