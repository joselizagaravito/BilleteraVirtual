package com.grupo08.yanki.service;

import java.util.List;
import java.util.Optional;

import com.grupo08.yanki.models.Wallet;

public interface IWalletService {

	    public List<Wallet> findAll();
	    public Optional<Wallet> findById(Long id);
	    public List<Wallet> findByNumdocument(String numdocument);
	    public Wallet update(Wallet wallet);
	    public Wallet save(Wallet wallet);
	    public void delete(Wallet wallet);
	    public Wallet findWalletById(Long id);
	    
}