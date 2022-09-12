package com.grupo08.card.models;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "currentacccount")
public class CurrentAccount {

	@Id
	private Long id;
	private String type;
	private String customerId;
	private LocalDate createdAt;
	private LocalDate updatedAt;
	private float availableBalance;

}
