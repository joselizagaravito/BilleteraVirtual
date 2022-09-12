package com.grupo08.card.models;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tarjetas")
public class Card {
	@Id
	private String id;
	private String numdocument;
	private String typecard; //d=debito; c=credito
    @NonNull
	private String principalaccount;
    private String currentaccount;
    private String fixedtermsavingsaccount;
    private String savingsAccount;
    private LocalDate createdAtDate;
    private LocalDate updatedAt;
}
