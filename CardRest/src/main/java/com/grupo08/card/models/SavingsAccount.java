package com.grupo08.card.models;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SavingsAccount{
    @Id
    private Long id;
    private String type;
    private String customerId;
    @CreatedDate
    private LocalDate createdAt;
    @LastModifiedDate
    private LocalDate updatedAt;
    private float  availableBalance;
}
