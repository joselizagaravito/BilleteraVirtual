package com.grupo04.account.models;

import java.time.LocalDate;

import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {
	@Id
	private String id;
	private String numdocument;
	private String typecard; //d=debito; c=credito
	private String principalaccount;
    private String currentaccount;
    private String fixedtermsavingsaccount;
    private String savingsAccount;
    private LocalDate createdAtDate;
    private LocalDate updatedAt;
	
}
