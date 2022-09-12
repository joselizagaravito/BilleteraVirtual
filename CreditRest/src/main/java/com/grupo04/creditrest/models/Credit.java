package com.grupo04.creditrest.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerid;
    @Column(name = "typecredit")
    private String typecredit;
    @Column(name = "creditamount")
    private float creditAmount;
    @Column(name = "amountused")
    private float amountUsed;
    @Column(name = "createdat")
    private Date createdAt;
}
