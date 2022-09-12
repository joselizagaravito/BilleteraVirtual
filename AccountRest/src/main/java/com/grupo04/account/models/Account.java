package com.grupo04.account.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="acccount")
public class Account{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    @NotBlank(message ="Account.customerId no debe ser blanco")
    @Column(name="customerid")
    private String customerId;
    @Column(name="typeprofileaccount")
    private String typeProfileAccount;
    @Column(name="commissionstatus")
    private boolean commissionStatus;
    @Column(name="commissionamount")
    private double commissionAmount;
    @Column(name="limittransaction")
    private int limitTransaction;
    
    @CreatedDate
    @Column(name="createat")
    private LocalDate createdAt;
    
    @LastModifiedDate
    @Column(name="updatedat")
    private LocalDate updatedAt;
    
    @Column(name="available")
    private float  availableBalance;    
}
