package com.grupo08.yanki.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grupo08.yanki.models.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long>{
	public List<Wallet> findByNumdocument(String numdocument);
}
