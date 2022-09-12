package com.grupo08.yanki.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo08.yanki.models.Wallet;
import com.grupo08.yanki.service.IWalletService;

@RestController
@RequestMapping("/yanki")
public class WalletController {

	@Autowired
	IWalletService service;

	@GetMapping
	public List<Wallet> listar(Model model) {
		return service.findAll();
	}

	@GetMapping("/detail/{id}")
	public Optional<Wallet> detail(@PathVariable Long id) {
		return service.findById(id);
	}

	@PutMapping
	public Wallet update(@RequestBody Wallet wallet) {
		return service.update(wallet);
	}

	@PostMapping
	public Wallet create(@RequestBody Wallet wallet) {
		return service.save(wallet);
	}
	
	@DeleteMapping
	public void delete(@RequestBody Wallet wallet) {
		service.delete(wallet);
	}
	
	@GetMapping("/redisdetail/{id}")
	public Wallet walletforDetail(@PathVariable Long id) {
		return service.findWalletById(id);
	}
}
