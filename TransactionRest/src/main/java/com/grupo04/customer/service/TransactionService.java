package com.grupo04.customer.service;

import com.grupo04.customer.kafka.TransactionProducer;
import com.grupo04.customer.models.Account;
import com.grupo04.customer.models.Card;
import com.grupo04.customer.models.ResponseAccounts;
import com.grupo04.customer.models.Transaction;
import com.grupo04.customer.repository.TransactionRepository;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionService implements ITransactionService {

	@Autowired
	private TransactionRepository repository;

	@Autowired
	private RestTemplate clientRest;

	private final WebClient webClient;

	//@Autowired
	private final TransactionProducer producer;

	@Value("${account.url}")
	private String urlAccount;

	@Value("${card.url}")
	private String urlc;

	public TransactionService(WebClient.Builder webBuilder, TransactionProducer producer) {
		this.producer=producer;
		this.webClient = webBuilder.baseUrl("http://localhost:9040/card/").build();
	}

	@Override
	public Flux<Transaction> findAll() {
		return repository.findAll();
	}

	@Override
	public Mono<Transaction> findById(String id) {
		return repository.findById(id);
	}

	@Override
	public Mono<Transaction> save(Transaction transaction) {

		producer.sendMessage("primera linea ");
		ResponseAccounts responseAccount = getAccount(transaction.getSourceAccount());
		System.out.println("valor de responseAccount: " + responseAccount);

		Long counterTransaction = countTransactionInActualMonth(transaction.getSourceAccount());

		if (responseAccount.isPresent()) {
			Account accountResult = responseAccount.getAccount();

			if (accountResult.getAvailableBalance() > transaction.getAmount()) {
				Account targetAccount = getAccount(transaction.getTargetAccount()).getAccount();
				float newAmount = transaction.getAmount();
				if (accountResult.isCommissionStatus()) {
					newAmount = (float) (transaction.getAmount() - accountResult.getCommissionAmount());
					transaction.setAmount(newAmount);
				}
				if (counterTransaction + 1 == accountResult.getLimitTransaction()) {
					accountResult.setCommissionStatus(true);
				}
				accountResult.setAvailableBalance(accountResult.getAvailableBalance() - newAmount);
				targetAccount.setAvailableBalance(targetAccount.getAvailableBalance() + newAmount);

				System.out.println(urlAccount.concat(accountResult.getId().toString()));
				System.out.println(urlAccount.concat(targetAccount.getId().toString()));
				System.out.println("accountResult:" + accountResult.toString());
				System.out.println("targetAccount:" + targetAccount.toString());

				clientRest.put(urlAccount.concat(accountResult.getId().toString()), accountResult);
				clientRest.put(urlAccount.concat(targetAccount.getId().toString()), targetAccount);

				// transaction.setType("Retiro");
				transaction.setDate(LocalDateTime.now());
				Mono<Transaction> result = repository.save(transaction);
				System.out.println("result: " + result);
				producer.sendMessage("para transaction");
				return result;
			}
		}
		return null;
	}

	@Override
	public Mono<Void> delete(Transaction transaction) {
		return repository.delete(transaction);
	}

	private ResponseAccounts getAccount(String accountNumber) {
		System.out.println("url account: " + urlAccount.concat("numaccount/").concat(accountNumber));
		return clientRest.getForObject(urlAccount.concat("numaccount/").concat(accountNumber), ResponseAccounts.class);
	}

	private Long countTransactionInActualMonth(String accountNumber) {
		return repository.findBySourceAccount(accountNumber)
				.filter(p -> p.getDate().getMonth().compareTo(LocalDateTime.now().getMonth()) == 0
						&& p.getDate().getYear() == LocalDateTime.now().getYear())
				.count().toFuture().join();
	}

	@Override
	public Flux<Transaction> listlastten(String documentnumber) {
		Flux<Card> responseCard = this.webClient.get().uri("document/" + documentnumber).retrieve()
				.bodyToFlux(Card.class);

		return responseCard.flatMap(p -> {
			p.setPrincipalAccount("20220002001");
			return repository.findTop10BySourceAccountOrderByDateDesc(p.getPrincipalAccount());
		});
	}

}
