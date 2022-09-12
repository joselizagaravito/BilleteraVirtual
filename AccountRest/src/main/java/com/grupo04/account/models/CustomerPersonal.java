package com.grupo04.account.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Data
public class CustomerPersonal{
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String dni;
    private String email;
    private String phoneNumber;
    private boolean state;
    private LocalDate createdAt;
    private LocalDate updatedAt;

}