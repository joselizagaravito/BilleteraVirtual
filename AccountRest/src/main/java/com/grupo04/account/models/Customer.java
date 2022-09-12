package com.grupo04.account.models;

import java.util.Optional;

public final class Customer {
	public Optional<Object> createCustomer(String tipo) {
		if (tipo == "Personal") {
			return Optional.of(createCustomerPersonal());
		} else if (tipo == "Business") {
			return Optional.of(createCustomerBusiness());
		} else {
			return Optional.empty();
		}
	}

	public static CustomerBusiness createCustomerBusiness() {
		return new CustomerBusiness();
	}

	public static CustomerPersonal createCustomerPersonal() {
		return new CustomerPersonal();
	}

}