package com.grupo08.card.models;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;

import lombok.Data;

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