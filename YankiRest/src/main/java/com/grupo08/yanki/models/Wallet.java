package com.grupo08.yanki.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Wallet implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    private String numdocument;
    private String cell;
    private String imei;
    private String email;
    private String accountid;
    private float amountavailable;
    
    private static final long serialVersionUID = 1L;
}
