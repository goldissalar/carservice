package com.example.carservice.kafka.carProducer;

import com.example.carservice.DTO.CarDTO;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

@Slf4j
@NoArgsConstructor
@Component
public class CarProducer {
    final String carTopic = "car";

    private KafkaTemplate<String, Serializable> kafkaTemplate;

    @Autowired
    public CarProducer(KafkaTemplate<String, Serializable> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(CarDTO message) {
        CompletableFuture<SendResult<String, Serializable>> future = kafkaTemplate.send(carTopic, message);
        future.whenComplete(new BiConsumer<SendResult<String, Serializable>, Throwable>() {
            @Override
            public void accept(SendResult<String, Serializable> stringSerializableSendResult, Throwable throwable) {
                log.info("Message sent successfully with offset = {}", stringSerializableSendResult.getRecordMetadata().offset());
            }
        });
    }

}