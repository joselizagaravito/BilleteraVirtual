package com.grupo04.account.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.grupo04.account.models.Account;
import com.grupo04.account.repository.IAccountRepository;

class CurrentAccountServiceTest {

	private IAccountRepository mockitoRepository;

	private AccountService service;
	
	List<Account> listaMockito ;

	@BeforeEach
	void setup() {
		mockitoRepository = mock(IAccountRepository.class);
		service = new AccountService(mockitoRepository);
		listaMockito = Arrays.asList(
				new Account(1L, "ca", "123","345435435", "personal", false, 5, 20, LocalDate.of(2022, 01, 01), LocalDate.of(2022, 01, 01), 1000f),
				new Account(2L, "ca", "124","345435436", "personal", false, 5, 20, LocalDate.of(2022, 01, 02), LocalDate.of(2022, 01, 02), 1500f),
				new Account(1L, "ca", "125","345435437", "personal", false, 5, 20, LocalDate.of(2022, 01, 01), LocalDate.of(2022, 01, 03), 1600f),
				new Account(2L, "ca", "126","345435438", "personal", false, 5, 20, LocalDate.of(2022, 01, 02), LocalDate.of(2022, 01, 04), 1700f));

	}
	
	@Test
	void testFindAll() {
		
		when(mockitoRepository.findAll()).thenReturn(listaMockito);

		Integer expected = 4;
		List<Account> actual = service.findAll();
		assertEquals(expected, actual.size());

	}

	@Test
	void findByIdtest() {
		
		when(mockitoRepository.findById(2L)).thenReturn(Optional.of(listaMockito.get(0)));

		Account current = new Account(1L, "ca", "123","345435435", "personal", false, 5, 20, LocalDate.of(2022, 01, 01),
				LocalDate.of(2022, 01, 01), 1000f);
		Optional<Account> expected = Optional.of(current);
		Optional<Account> actual = service.findById(2L);
		assertEquals(expected, actual);
	}

	@Test
	void findByCustomerIdtest() {
		
		when(mockitoRepository.findByCustomerId("123")).thenReturn(listaMockito);

		Integer expected = 4;
		List<Account> actual = service.findByCustomerId("123");
		assertEquals(expected, actual.size());
	}

	@Test
	void savetest() {
	
	}
}
