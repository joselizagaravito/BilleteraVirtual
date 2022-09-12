package com.grupo04.account.controllers;

import com.grupo04.account.models.Account;
import com.grupo04.account.models.ResponseAccounts;
import com.grupo04.account.service.IAccountService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

@RestController
@RequestMapping("/account")
@Slf4j
public class AccountController {

	@Autowired
	private IAccountService accountService;

	@GetMapping("/{numaccount}")
	public ResponseEntity<ResponseAccounts> accounts(@PathVariable Long numaccount) {

		String descripciontipo = "";
		Optional<Account> opaccount = accountService.findById(numaccount);
		String tipo = opaccount.get().getType();

		log.info("Descripcion del tipo: " + tipo);
		
		if (tipo.equals("ca"))
			descripciontipo = "CurrentAccount";
		if (tipo.equals("ft"))
			descripciontipo = "FixedTermSavingsAccount";
		if (tipo.equals("sa"))
			descripciontipo = "SavingsAccount";

		if (opaccount.isPresent()) {
			return ResponseEntity.ok().header("Cuenta: ", numaccount.toString())
					.body(new ResponseAccounts(opaccount.get(), true, descripciontipo));

		} else {
			return ResponseEntity.ok().header("Sin cuenta", numaccount.toString()).body(null);
		}
	}

	@GetMapping
	public List<Account> listar(Model model) {
		return accountService.findAll();
	}
	/*
	 * @GetMapping("/{id}") public Optional<Account> detail(@PathVariable Long id) {
	 * return accountService.findById(id); }
	 */

	public Optional<Account> detailA(Long id) {
		return accountService.findById(id);
	}

	public Optional<Account> detailB(Long id) {
		return accountService.findById(id);
	}

	@PostMapping("/save")
	public ResponseEntity<Map<String, Object>> save(@Valid @RequestBody Account monoAccount) {
		Map<String, Object> result = new HashMap<String, Object>();
		log.info(monoAccount.toString());

		Account p = accountService.save(monoAccount).get();
		if (p.getId() == null) {
			result.put("customer", monoAccount.getCustomerId());
			result.put("account", null);
			result.put("message", "No se genero la cuenta");
			result.put("status", false);
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(result);
		} else {
			result.put("customer", monoAccount.getCustomerId());
			result.put("account", p);
			result.put("status", true);
			result.put("message", "Cuenta corriente guardada con éxito");
			return ResponseEntity.created(URI.create("/api/savings/".concat(p.getId().toString())))
					.contentType(MediaType.APPLICATION_JSON).body(result);
		}
	}

	@GetMapping("/find/{customerid}")
	public List<Account> findByCustomer(@PathVariable String customerid) {
		List<Account> accounts = accountService.findByCustomerId(customerid);
		return accounts;
	}
	

	@PutMapping("/{id}")
	public Account edit(@RequestBody Account savings, @PathVariable Long id) {
		Account p = accountService.findById(id).get();
		p.setAvailableBalance(savings.getAvailableBalance());
		p.setUpdatedAt(LocalDate.now());
		return accountService.save(p).get();
	}

	@DeleteMapping("/{id}")
	public void edit(@PathVariable Long id) {
		Account p = accountService.findById(id).get();
		accountService.delete(p);
	}
}