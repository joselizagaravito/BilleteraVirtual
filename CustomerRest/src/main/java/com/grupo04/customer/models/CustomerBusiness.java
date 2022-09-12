package com.grupo04.customer.models;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "customerbusiness")
public class CustomerBusiness{

    @Id
    private String id;
    @NonNull
    private String name;
    @NonNull
    private String ruc;
    private String address;
    private String phoneNumber;
    @CreatedDate
    private LocalDate createdAt;
    @LastModifiedDate
    private LocalDate updatedAt;

}
