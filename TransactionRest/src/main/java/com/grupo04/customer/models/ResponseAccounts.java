package com.grupo04.customer.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseAccounts {
	
	private Account savingsAccount;
    private boolean isPresent;
    private String typeAccount;

}
