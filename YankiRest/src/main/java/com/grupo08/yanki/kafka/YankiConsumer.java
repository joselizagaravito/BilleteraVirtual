package com.grupo08.yanki.kafka;

import java.io.IOException;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class YankiConsumer {
	
    //@KafkaListener(topics = "${events.topic.input.pattern}", groupId = "group_id")
    @KafkaListener(topics = "tTransaction", groupId = "group01")
    public void consume(String producto) throws IOException {
        //logica de negocio
        log.info(String.format("#### CONSUMER ##### -> Consumed message -> %s", producto));
    }
}
