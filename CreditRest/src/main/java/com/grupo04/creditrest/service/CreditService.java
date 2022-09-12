package com.grupo04.creditrest.service;

import com.grupo04.creditrest.models.Credit;
import com.grupo04.creditrest.repository.ICreditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CreditService implements ICreditService {
    @Autowired
    private ICreditRepository creditRepository;

    @Override
    public List<Credit> findAllBusinesscredit() {
        return creditRepository.findByTypecredit("business");
    }

    @Override
    public List<Credit> findAllPersonalcredit() {
        return creditRepository.findByTypecredit("personal");
    }
    @Override
    public Optional<Credit> findById(Long id) {
        return creditRepository.findById(id);
    }

    @Override
    public List<Credit> findByCustomerId(String customerid) {
        return creditRepository.findByCustomerid(customerid);
    }

    @Override
    public Credit save(Credit credit) {
        return creditRepository.save(credit);
    }

    @Override
    public void delete(Credit credit) {
        creditRepository.delete(credit);
    }
}
