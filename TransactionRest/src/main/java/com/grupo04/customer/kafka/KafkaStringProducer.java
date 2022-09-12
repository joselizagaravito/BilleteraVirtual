package com.grupo04.customer.kafka;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KafkaStringProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaStringProducer(@Qualifier("kafkaStringTemplate") KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {
        log.info("Producing message {}", message);
        this.kafkaTemplate.send("tTransaction", message);
    }
}
