package com.grupo04.account.models;

import lombok.Data;
import lombok.NonNull;


@Data
public class RequestCustomerDto{
    @NonNull
    private String customerId;
    private boolean state;
}
