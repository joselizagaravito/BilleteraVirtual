package com.grupo04.creditrest.models;

import java.time.LocalDate;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usecard {
    private String id;
    private String sourceAccount;
    private float amount;
    private LocalDate date;
    private String dni;
}
