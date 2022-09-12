package com.grupo04.account.service;

import com.grupo04.account.models.Account;
import com.grupo04.account.models.Card;
import com.grupo04.account.models.CardList;
import com.grupo04.account.models.CustomerBusiness;
import com.grupo04.account.models.CustomerPersonal;
import com.grupo04.account.models.ResponseAccounts;
import com.grupo04.account.models.ResponseProducts;
import com.grupo04.account.repository.IAccountRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import javax.websocket.EncodeException;

@Service
@Slf4j
public class AccountService implements IAccountService {

	@Value("${customer.personal.url}")
	private String urlp;

	@Value("${customer.business.url}")
	private String urlb;

	@Value("${card.validate.url}")
	private String urlc;

	@Autowired
	private RestTemplate clientRest;

	@Autowired
	private IAccountRepository accountRepository;

	public AccountService(IAccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Override
	public List<Account> findAll() {
		log.info("prueba de findAll");
		return accountRepository.findAll();
	}

	@Override
	public Optional<Account> findById(Long id) {
		return accountRepository.findById(id);
	}

	@Override
	public List<Account> findByCustomerId(String customerId) {
		return accountRepository.findByCustomerId(customerId);
	}

	@Override
	public Optional<Account> save(Account account) {
		log.info(account.toString());
		// buscar el tipo de cliente
		CustomerPersonal p = clientRest.getForObject(urlp.concat(account.getCustomerId()), CustomerPersonal.class);
		log.info(p.toString());
		CustomerBusiness b = clientRest.getForObject(urlb.concat(account.getCustomerId()), CustomerBusiness.class);
		log.info(b.toString());

		// si no es cliente del banco
		if (p.getId() == null && b.getId() == null)
			return Optional.of(null);

		// si es business o pyme
		if (b.getId() != null) {
			List<Card> listCard = clientRest.getForObject(urlc.concat(b.getRuc()), List.class);
			boolean haveCreditCard = listCard.stream().filter(card -> card.getTypecard().equalsIgnoreCase("c"))
					.count() > 0;

			if (account.getTypeProfileAccount().equalsIgnoreCase("pyme") && haveCreditCard
					|| account.getTypeProfileAccount().equalsIgnoreCase("business")) {
				return Optional.of(accountRepository.save(account));
			}
		}

		return Optional.of(null);
	}

	@Override
	public void delete(Account account) {
		accountRepository.delete(account);
	}

	@Override
	public ResponseEntity<ResponseProducts> findProductsByDocument(String document) {
		ResponseProducts result = new ResponseProducts();

		String idCustomer = "";

		CustomerPersonal customerPersonal = clientRest.getForObject(urlp.concat("dni/").concat(document),
				CustomerPersonal.class);
		if (customerPersonal != null) {
			result.setCustomerPersonal(customerPersonal);
			log.info("CustomerPersonal: " + customerPersonal.toString());
			idCustomer = customerPersonal.getId();
		}

		CustomerBusiness customerBusiness = clientRest.getForObject(urlb.concat("/ruc/").concat(document),
				CustomerBusiness.class);
		if (customerBusiness != null) {
			result.setCustomerBusiness(customerBusiness);
			log.info("CustomerBusiness: " + customerBusiness.toString());
			idCustomer = customerBusiness.getId();
		}

		log.info("urlc: " + urlc.concat(document));
		try {
			CardList cards = clientRest.getForObject(urlc.concat(document), CardList.class);
			if (cards != null) {
				result.setCards(cards.getCards());
				log.info("Cards: " + cards.toString());
			}
		} catch (Exception e) {
			log.info("Error en findProductsByDocument: " + e.getMessage());
		}

		try {
			List<Account> accounts = findByCustomerId(idCustomer);
			if (accounts != null) {
				result.setAccounts(accounts);
			}
		} catch (Exception e) {
			log.info("Error en findProductsByDocument: " + e.getMessage());
		}

		// Buscar cuentas personal y de negocio
		return ResponseEntity.ok().header("Productos de documento: ", document).body(result);
	}

}