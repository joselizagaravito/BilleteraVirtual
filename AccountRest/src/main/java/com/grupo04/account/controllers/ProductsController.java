package com.grupo04.account.controllers;

import com.grupo04.account.models.Account;
import com.grupo04.account.models.ResponseProducts;
import com.grupo04.account.service.IAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductsController{

	//@Autowired
	//private CircuitBreakerFactory cbFactory;

	@Autowired
	private IAccountService service;

	@GetMapping("/{document}")
	public ResponseEntity<ResponseProducts> listarProductos(@PathVariable String document) {
		log.info("Documento: " + document);
		return service.findProductsByDocument(document);
	}

	//Guardar Cuenta
	@PostMapping("/save")
	public ResponseEntity<Map<String, Object>> save(@Valid @RequestBody Account account) {
		Map<String, Object> result = new HashMap<String, Object>();
		log.info(account.toString());

		Account p = service.save(account).get();
		if (p.getId() == null) {
			result.put("customer", account.getCustomerId());
			result.put("account", null);
			result.put("message", "No se genero la cuenta");
			result.put("status", false);
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(result);
		} else {
			result.put("customer", account.getCustomerId());
			result.put("account", p);
			result.put("status", true);
			result.put("message", "Cuenta corriente guardada con éxito");
			return ResponseEntity.created(URI.create("/api/savings/".concat(p.getId().toString())))
					.contentType(MediaType.APPLICATION_JSON).body(result);
		}

	}

	@GetMapping("/find/{customerid}")
	public List<Account> findByCustomer(@PathVariable String customerid) {
		List<Account> accounts = service.findByCustomerId(customerid);
		return accounts;
	}

	@PutMapping("/{id}")
	public Optional<Account> edit(@RequestBody Account savings, @PathVariable Long id) {
		Account p = service.findById(id).get();
		p.setAvailableBalance(savings.getAvailableBalance());
		p.setUpdatedAt(LocalDate.now());
		return service.save(p);
	}

	@DeleteMapping("/{id}")
	public void edit(@PathVariable Long id) {
		Account p = service.findById(id).get();
		service.delete(p);
	}

}