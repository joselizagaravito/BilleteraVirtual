package com.grupo04.customer.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.grupo04.customer.models.Transaction;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TransactionProducer {
 private static final String TOPIC = "tTransaction";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String key, Transaction transaction) {
        log.info(String.format("#### PRODUCER #### -> Mensaje enviado -> key %s value %s",key, transaction.toString()));
        this.kafkaTemplate.send(TOPIC, key, transaction.toString());
    }
}
