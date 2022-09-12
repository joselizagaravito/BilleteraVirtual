package com.grupo08.card.models;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class CustomerBusiness{
    @Id
    private String id;
    private String name;
    private String ruc;
    private String address;
    private String phoneNumber;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}