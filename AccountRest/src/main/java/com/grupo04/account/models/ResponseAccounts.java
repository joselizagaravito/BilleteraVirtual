package com.grupo04.account.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;


@Data
@AllArgsConstructor
public class ResponseAccounts{
	@NonNull
    private Account account;
    private boolean isPresent;
    private String typeAccount;
}
