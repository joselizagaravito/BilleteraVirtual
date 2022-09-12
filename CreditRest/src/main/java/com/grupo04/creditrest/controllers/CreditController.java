package com.grupo04.creditrest.controllers;

import com.grupo04.creditrest.models.Credit;
import com.grupo04.creditrest.service.ICreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class CreditController {
	@Autowired
	private ICreditService service;

	@GetMapping("/personal")
	public List<Credit> listpersonal() {
		return service.findAllPersonalcredit();
	}

	@GetMapping("/business")
	public List<Credit> listbusiness() {
		return service.findAllBusinesscredit();
	}

	@GetMapping("/{id}")
	public Optional<Credit> buscarPorId(@PathVariable Long id) {
		return service.findById(id);
	}

	@PostMapping
	public Credit save(@RequestBody Credit credit) {
		return service.save(credit);
	}

	@PutMapping
	public Credit update(@RequestBody Credit credit) {
		Optional<Credit> p = service.findById(credit.getId());
		if (p.isPresent())
			return service.save(p.get());
		else
			return null;
	}

	@DeleteMapping
	public void delete(@RequestBody Credit credit) {
		service.delete(credit);
	}
}