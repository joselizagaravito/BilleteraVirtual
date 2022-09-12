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
    private TransactionProducer producer;
    
    @Value("${account.url}")
	private String urlAccount;
    
    @Value("${card.url}")
	private String urlc;
    
    public TransactionService(WebClient.Builder webBuilder) {
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
 
    	ResponseAccounts responseAccount = getAccount(transaction.getSourceAccount());
    	Long counterTransaction = countTransactionInActualMonth(transaction.getSourceAccount());
    	
    		if(responseAccount.isPresent()) {
    			Account accountResult=responseAccount.getSavingsAccount();
    			
    			if(accountResult.getAvailableBalance()>transaction.getAmount()) {
    				Account targetAccount = getAccount(transaction.getTargetAccount()).getSavingsAccount();
    				float newAmount= transaction.getAmount();
    				if(accountResult.isCommissionStatus()) {
    					newAmount = (float) (transaction.getAmount()-accountResult.getCommissionAmount());
        				transaction.setAmount(newAmount);
        			}
        			if(counterTransaction+1==accountResult.getLimitTransaction()) {
        				accountResult.setCommissionStatus(true);
        			}
        			accountResult.setAvailableBalance(accountResult.getAvailableBalance() - newAmount);
        			targetAccount.setAvailableBalance(targetAccount.getAvailableBalance() + newAmount);
        			clientRest.put(urlAccount,accountResult);
        			clientRest.put(urlAccount,targetAccount);
        			
        			//transaction.setType("Retiro");
        			transaction.setDate(LocalDateTime.now());
        			Mono<Transaction> result = repository.save(transaction);
        			String id = result.block().getId();
        			producer.sendMessage(id,transaction);
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
    	return clientRest.getForObject(urlAccount.concat(accountNumber),ResponseAccounts.class);
    }
    
    private Long countTransactionInActualMonth(String accountNumber){
    	return repository.findBySourceAccount(accountNumber)
    			.filter(p->p.getDate().getMonth().compareTo(LocalDateTime.now().getMonth())==0 &&
    					p.getDate().getYear()==LocalDateTime.now().getYear())
    			.count()
    			.toFuture()
    			.join();
    }
    
	@Override
	public Flux<Transaction> listlastten(String documentnumber) {
		Flux<Card> responseCard = this.webClient.get().uri("document/"+documentnumber).retrieve().bodyToFlux(Card.class);
		
		return responseCard.flatMap(p->{
			p.setPrincipalAccount("20220002001");
			return repository.findTop10BySourceAccountOrderByDateDesc(p.getPrincipalAccount());
		});
	}
   
}
