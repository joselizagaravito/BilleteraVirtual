package com.grupo04.creditrest.repository;

import com.grupo04.creditrest.models.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICreditRepository extends JpaRepository<Credit, Long> {
    public List<Credit> findByCustomerid(String customerid);
    public List<Credit> findByTypecredit(String typecredit);
}
